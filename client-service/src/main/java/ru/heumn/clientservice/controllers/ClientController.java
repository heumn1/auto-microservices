package ru.heumn.clientservice.controllers;

import org.springframework.http.ResponseEntity;
import ru.heumn.clientservice.storages.dto.ClientDtoRequest;
import ru.heumn.clientservice.storages.dto.ClientDtoResponse;
import ru.heumn.clientservice.storages.dto.OrderDtoResponse;
import ru.heumn.clientservice.storages.exceptions.BadRequestException;
import ru.heumn.clientservice.storages.exceptions.ConflictRequestException;
import ru.heumn.clientservice.storages.exceptions.NotFoundException;

import java.util.List;

public interface ClientController {
    ResponseEntity<List<ClientDtoResponse>> getAllClient();
    ResponseEntity<ClientDtoResponse> getAllClient(Long id) throws NotFoundException, BadRequestException;
    ResponseEntity<ClientDtoResponse> addClient(ClientDtoRequest clientDtoRequest);
    ResponseEntity<ClientDtoResponse> updateClient(Long id, ClientDtoRequest clientDtoRequest) throws NotFoundException, BadRequestException;
    ResponseEntity<Boolean> deleteClient(Long id) throws NotFoundException, BadRequestException;
    ResponseEntity<List<OrderDtoResponse>> getAllOrdersOfClient(Long id) throws BadRequestException, NotFoundException;
    ResponseEntity<ClientDtoResponse> addOrderToClient(Long idClient,Long idOrder) throws BadRequestException, ConflictRequestException, NotFoundException ;
    ResponseEntity<ClientDtoResponse> deleteOrderOfClient(Long idClient, Long idOrder) throws BadRequestException, NotFoundException;
}
