package ru.heumn.orderservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import ru.heumn.orderservice.factories.OrderDtoFactory;
import ru.heumn.orderservice.storages.dto.OrderDtoRequest;
import ru.heumn.orderservice.storages.dto.OrderDtoResponse;
import ru.heumn.orderservice.storages.entity.OrderEntity;
import ru.heumn.orderservice.storages.entity.PartEntity;
import ru.heumn.orderservice.storages.event.OrderCreatedEvent;
import ru.heumn.orderservice.storages.exceptions.BadConnectionException;
import ru.heumn.orderservice.storages.exceptions.BadRequestException;
import ru.heumn.orderservice.storages.exceptions.ConflictRequestException;
import ru.heumn.orderservice.storages.exceptions.NotFoundException;
import ru.heumn.orderservice.storages.repository.OrderRepository;
import ru.heumn.orderservice.storages.repository.PartRepository;
import ru.heumn.orderservice.util.HibernateUtil;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
    OrderRepository orderRepository = new OrderRepository(sessionFactory);
    PartRepository partRepository = new PartRepository(sessionFactory);

    private final WebClient.Builder webClientBuilder;

    public List<OrderDtoResponse> getAllOrders() {
        log.info("triggering method: getAllOrders");

        List<OrderEntity> orders = orderRepository.findAll();
        return OrderDtoFactory.makeDtoListOrder(orders);
    }

    public OrderDtoResponse getOrder(Long id) throws NotFoundException {
        log.info("triggering method: getOrder with id: " + id);

        OrderEntity order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found"));
        return OrderDtoFactory.makeDtoOrderResponse(order);
    }

    public OrderDtoResponse updateOrder(Long id, OrderDtoRequest orderDtoRequest) throws NotFoundException, BadRequestException {
        log.info("triggering method: updateOrder with id: " + id + "with parameters: " + orderDtoRequest);

        if(id <= 0) {
            throw new BadRequestException("bad id");
        }
        Optional<OrderEntity> orderEntity = orderRepository.findById(id);

        if(orderEntity.isEmpty()) {
            throw new NotFoundException("Order not found");
        }
        OrderEntity order = OrderDtoFactory.makeOrderEntity(orderDtoRequest);
        order.setPrice(orderEntity.get().getPrice());
        order.setParts(orderEntity.get().getParts());
        order.setId(id);

        orderRepository.update(order);
        return OrderDtoFactory.makeDtoOrderResponse(order);
    }

    public Boolean addOrder(OrderDtoRequest orderDtoRequest) {
        log.info("triggering method: addOrders");

        OrderEntity order = OrderDtoFactory.makeOrderEntity(orderDtoRequest);
        order.setPrice(0.0);
        orderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(order.getId(), order.getNumberClient());

        kafkaTemplate.send("orders-to-client-events-topic", "order-service", event);

        return true;
    }

    public Boolean deleteOrder(Long id) throws BadRequestException, NotFoundException {
        log.info("triggering method: deleteOrders id: " + id);

        if(id <= 0) {
            throw new BadRequestException("bad id");
        }
        else {
            Optional<OrderEntity> order = orderRepository.findById(id);
            if(order.isEmpty()) {
                throw new NotFoundException("part not found");
            }
            else {
                orderRepository.delete(order.get());
                return true;
            }
        }
    }

    private Object checkPartAvailabilityInStock(Long idPart)throws Exception {
        log.info("triggering method: checkPartAvailabilityInStock idPart: " + idPart);
        Boolean response = webClientBuilder.build().get()
                .uri("http://storage-service/api/storage/part/" + idPart + "/is-in-stock")
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        return Boolean.TRUE.equals(response);
    }

    @Transactional
    public OrderDtoResponse addPartToOrder(Long idPart, Long idOrder) throws BadRequestException, NotFoundException, ConflictRequestException, BadConnectionException {
        log.info("triggering method: addPartToOrder idPart: " + idPart + ", idOrder:" + idOrder);

        if(idPart == null || idPart <= 0 || idOrder == null || idOrder <= 0) {
            throw new BadRequestException("idPart or idOrder equals null or < 0");
        }

        try {
            if(Boolean.FALSE.equals(checkPartAvailabilityInStock(idPart)))
            {
                throw new ConflictRequestException("The parts are not in stock");
            }
        }
        catch (Exception e) {
            throw new BadConnectionException("Bad Connection");
        }

        Optional<OrderEntity> order = orderRepository.findById(idOrder);
        Optional<PartEntity> part = partRepository.findById(idPart);

        if(order.isPresent() && part.isPresent()) {
            List<PartEntity> parts = order.get().getParts();
            parts.add(part.get());
            order.get().setParts(parts);

            Boolean response = webClientBuilder.build().patch()
                    .uri("http://storage-service/api/storage/part/" + idPart + "/deduct")
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

            if(Boolean.TRUE.equals(response)){
                try {
                    orderRepository.update(order.get());
                }
                catch (Exception e){
                    webClientBuilder.build().patch()
                            .uri("http://storage-service/api/storage/part/" + idPart + "/augment")
                            .retrieve()
                            .bodyToMono(Boolean.class)
                            .block();
                }
            }
            else {
                throw new BadRequestException("The quantity of parts is currently 0");
            }
            return OrderDtoFactory.makeDtoOrderResponse(order.get());
        }
        else {
            throw new NotFoundException("Order or part not found");
        }
    }

    public OrderDtoResponse deletePartToOrder(Long idPart, Long idOrder) throws BadRequestException, NotFoundException {
        log.info("triggering method: deletePartToOrder idPart: " + idPart + ", idOrder:" + idOrder);

        if(idPart == null || idPart <= 0 || idOrder == null || idOrder <= 0) {
            throw new BadRequestException("idPart or idOrder equals null or < 0");
        }

        Optional<OrderEntity> order = orderRepository.findById(idOrder);
        Optional<PartEntity> part = partRepository.findById(idPart);

        if(order.isPresent() && part.isPresent()) {
            List<PartEntity> parts = order.get().getParts();
            if(parts.contains(part.get())){

                webClientBuilder.build().patch()
                        .uri("http://storage-service/api/storage/part/" + idPart + "/augment")
                        .retrieve()
                        .bodyToMono(Boolean.class)
                        .block();

                parts.remove(part.get());
                order.get().setParts(parts);
                orderRepository.update(order.get());
                return OrderDtoFactory.makeDtoOrderResponse(order.get());
            }
            else {
                throw new NotFoundException("part in order not found");
            }
        }
        else {
            throw new NotFoundException("Order or part not found");
        }
    }
}
