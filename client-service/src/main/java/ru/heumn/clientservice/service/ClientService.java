package ru.heumn.clientservice.service;

import org.springframework.stereotype.Service;
import ru.heumn.clientservice.storages.dto.ClientDtoRequest;
import ru.heumn.clientservice.storages.dto.ClientDtoResponse;
import ru.heumn.clientservice.storages.dto.OrderDtoResponse;
import ru.heumn.clientservice.storages.exceptions.BadRequestException;
import ru.heumn.clientservice.storages.exceptions.ConflictRequestException;
import ru.heumn.clientservice.storages.exceptions.NotFoundException;

import java.util.List;

public interface ClientService {
    List<ClientDtoResponse> getAllClients();
    ClientDtoResponse getClientById(Long idClient) throws NotFoundException;
    ClientDtoResponse addClient(ClientDtoRequest clientDtoRequest) throws BadRequestException;
    ClientDtoResponse updateClient(Long idClient, ClientDtoRequest clientDtoRequest) throws BadRequestException, NotFoundException;
    Boolean deleteClient(Long idClient) throws NotFoundException;
    List<OrderDtoResponse> getOrdersByClient(Long idClient) throws NotFoundException;
    ClientDtoResponse addOrderToClient(Long idClient, Long idOrder) throws ConflictRequestException, NotFoundException;
    ClientDtoResponse deleteOrdersByClient(Long idClient, Long idOrder) throws NotFoundException;

    ClientDtoResponse addOrderToClientByNumber(String number, Long idOrder) throws NotFoundException;
}
