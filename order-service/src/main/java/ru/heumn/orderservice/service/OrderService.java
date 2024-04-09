package ru.heumn.orderservice.service;

import ru.heumn.orderservice.storages.dto.OrderDtoRequest;
import ru.heumn.orderservice.storages.dto.OrderDtoResponse;
import ru.heumn.orderservice.storages.exceptions.BadConnectionException;
import ru.heumn.orderservice.storages.exceptions.BadRequestException;
import ru.heumn.orderservice.storages.exceptions.ConflictRequestException;
import ru.heumn.orderservice.storages.exceptions.NotFoundException;

import java.util.List;

public interface OrderService {

    List<OrderDtoResponse> getAllOrders();
    OrderDtoResponse getOrder(Long id) throws NotFoundException;
    OrderDtoResponse updateOrder(Long id, OrderDtoRequest orderDtoRequest) throws NotFoundException, BadRequestException;
    Boolean addOrder(OrderDtoRequest orderDtoRequest);
    Boolean deleteOrder(Long id) throws BadRequestException, NotFoundException;
    OrderDtoResponse addPartToOrder(Long idPart, Long idOrder) throws BadRequestException, NotFoundException, ConflictRequestException, BadConnectionException;
    OrderDtoResponse deletePartToOrder(Long idPart, Long idOrder) throws BadRequestException, NotFoundException;
}
