package ru.heumn.clientservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.heumn.clientservice.factories.ClientDtoFactory;
import ru.heumn.clientservice.factories.OrderDtoFactory;
import ru.heumn.clientservice.storages.dto.ClientDtoRequest;
import ru.heumn.clientservice.storages.dto.ClientDtoResponse;
import ru.heumn.clientservice.storages.dto.OrderDtoResponse;
import ru.heumn.clientservice.storages.entity.ClientEntity;
import ru.heumn.clientservice.storages.entity.OrderEntity;
import ru.heumn.clientservice.storages.exceptions.BadRequestException;
import ru.heumn.clientservice.storages.exceptions.ConflictRequestException;
import ru.heumn.clientservice.storages.exceptions.NotFoundException;
import ru.heumn.clientservice.storages.repository.ClientRepository;
import ru.heumn.clientservice.storages.repository.OrderRepository;
import ru.heumn.clientservice.util.HibernateUtil;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService{

    ClientRepository clientRepository = new ClientRepository(HibernateUtil.buildSessionFactory());
    OrderRepository orderRepository = new OrderRepository(HibernateUtil.buildSessionFactory());

    @Override
    public List<ClientDtoResponse> getAllClients() {
        return ClientDtoFactory.makeDtoListClient(clientRepository.findAll());
    }

    @Override
    public ClientDtoResponse getClientById(Long idClient) throws NotFoundException {
        ClientEntity client = clientRepository.findById(idClient).orElseThrow(() -> new NotFoundException("client not found"));
        return ClientDtoFactory.makeClientDtoResponse(client);
    }

    @Override
    public ClientDtoResponse addClient(ClientDtoRequest clientDtoRequest){
        ClientEntity client = ClientDtoFactory.makeClientEntity(clientDtoRequest);
        clientRepository.save(client);
        return ClientDtoFactory.makeClientDtoResponse(client);
    }

    @Override
    public ClientDtoResponse updateClient(Long idClient, ClientDtoRequest clientDtoRequest) throws NotFoundException {

        ClientEntity client = clientRepository.findById(idClient).orElseThrow(() -> new NotFoundException("client not found"));

        client.setName(clientDtoRequest.getName());
        client.setEmail(clientDtoRequest.getEmail());
        client.setLastname(clientDtoRequest.getLastname());
        client.setPatronymic(clientDtoRequest.getPatronymic());
        client.setNumber(clientDtoRequest.getNumber());

        clientRepository.update(client);

        return ClientDtoFactory.makeClientDtoResponse(client);
    }

    @Override
    public Boolean deleteClient(Long idClient) throws NotFoundException {
        ClientEntity client = clientRepository.findById(idClient).orElseThrow(() -> new NotFoundException("Client not found"));
        clientRepository.delete(client);
        return true;
    }

    @Override
    public List<OrderDtoResponse> getOrdersByClient(Long idClient) throws NotFoundException {
        ClientEntity client = clientRepository.findById(idClient).orElseThrow(() -> new NotFoundException("Client not found"));
        return OrderDtoFactory.makeDtoListOrder(client.getOrders());
    }

    @Override
    public ClientDtoResponse addOrderToClient(Long idClient, Long idOrder) throws ConflictRequestException, NotFoundException {
        ClientEntity client = clientRepository.findById(idClient).orElseThrow(() -> new NotFoundException("Client not found"));
        OrderEntity order = orderRepository.findById(idOrder).orElseThrow(() -> new NotFoundException("Order not found"));

        Set<OrderEntity> ordersClient = client.getOrders();
        ordersClient.add(order);
        client.setOrders(ordersClient);

        clientRepository.update(client);

        return ClientDtoFactory.makeClientDtoResponse(client);
    }

    @Override
    public ClientDtoResponse deleteOrdersByClient(Long idClient, Long idOrder) throws NotFoundException {
        ClientEntity client = clientRepository.findById(idClient).orElseThrow(() -> new NotFoundException("Client not found"));
        OrderEntity order = orderRepository.findById(idOrder).orElseThrow(() -> new NotFoundException("Order not found"));

        Set<OrderEntity> ordersClient = client.getOrders();
        ordersClient.remove(order);
        client.setOrders(ordersClient);

        clientRepository.update(client);

        return ClientDtoFactory.makeClientDtoResponse(client);
    }

    @Override
    public ClientDtoResponse addOrderToClientByNumber(String number, Long idOrder) throws NotFoundException {

        ClientEntity client = clientRepository.getClientByNumber(number)
                .orElseThrow(() -> new NotFoundException("Client not found"));

        OrderEntity order = orderRepository.findById(idOrder)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        Set<OrderEntity> ordersClient = client.getOrders();
        ordersClient.add(order);
        client.setOrders(ordersClient);

        clientRepository.update(client);

        return ClientDtoFactory.makeClientDtoResponse(client);
    }
}
