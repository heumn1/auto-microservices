package ru.heumn.clientservice.handler;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.heumn.clientservice.service.ClientServiceImpl;
import ru.heumn.clientservice.storages.event.OrderCreatedEvent;
import ru.heumn.clientservice.storages.exceptions.NotFoundException;

@Component
// if you need to use a specific factory, set the property "containerFactory = "nameFactory11111" "
@KafkaListener(topics = "orders-to-client-events-topic")
@Log4j2
@RequiredArgsConstructor
public class OrdersToClientTopicHandler {

    private final ClientServiceImpl clientService;

    @KafkaHandler
    public void handler(OrderCreatedEvent event) throws NotFoundException {
        log.info("triggering handler OrderCreateEvent in orders-to-client-events-topic");
        System.out.println(event);
        throw new NotFoundException("ааааа");
//        clientService.addOrderToClientByNumber(event.getClientNumber(), event.getOrderId());
    }

}
