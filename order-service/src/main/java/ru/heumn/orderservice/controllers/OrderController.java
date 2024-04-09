package ru.heumn.orderservice.controllers;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.heumn.orderservice.service.OrderServiceImpl;
import ru.heumn.orderservice.storages.dto.OrderDtoRequest;
import ru.heumn.orderservice.storages.dto.OrderDtoResponse;
import ru.heumn.orderservice.storages.exceptions.BadConnectionException;
import ru.heumn.orderservice.storages.exceptions.BadRequestException;
import ru.heumn.orderservice.storages.exceptions.ConflictRequestException;
import ru.heumn.orderservice.storages.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api/order/")
@RequiredArgsConstructor
public class OrderController {


    public ResponseEntity<OrderDtoResponse> fallback(BadConnectionException badConnectionException) {
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    @Autowired
    OrderServiceImpl orderServiceImpl;

    @GetMapping()
    public ResponseEntity<List<OrderDtoResponse>> getAllOrders(){
        return new ResponseEntity<>(orderServiceImpl.getAllOrders(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderDtoResponse> getOrderById(@PathVariable Long id)
            throws NotFoundException {
        return new ResponseEntity<>(orderServiceImpl.getOrder(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderDtoResponse> updateOrder(@PathVariable Long id,
                                                        @RequestBody @Valid OrderDtoRequest orderDtoRequest,
                                                        BindingResult bindingResult)
            throws NotFoundException, BadRequestException {
        hasErrorDto(bindingResult);
        return new ResponseEntity<>(orderServiceImpl.updateOrder(id, orderDtoRequest), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Boolean> addOrder(@RequestBody @Valid OrderDtoRequest orderDtoRequest, BindingResult bindingResult) throws BadRequestException {
        hasErrorDto(bindingResult);
        return new ResponseEntity<>(orderServiceImpl.addOrder(orderDtoRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteOrder(@PathVariable Long id)
            throws BadRequestException, NotFoundException {
        return new ResponseEntity<>(orderServiceImpl.deleteOrder(id),HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{idOrder}/parts")
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallback")
    public ResponseEntity<OrderDtoResponse> addPartToOrder(@PathVariable Long idOrder, @RequestParam Long idPart)
            throws BadRequestException, NotFoundException, ConflictRequestException, BadConnectionException {
        return new ResponseEntity<>(orderServiceImpl.addPartToOrder(idPart,idOrder), HttpStatus.OK);
    }

    @DeleteMapping("/{idOrder}/parts")
    public ResponseEntity<OrderDtoResponse> deletePartToOrder(@PathVariable Long idOrder, @RequestParam Long idPart)
            throws BadRequestException, NotFoundException {
        return new ResponseEntity<>(orderServiceImpl.deletePartToOrder(idPart,idOrder), HttpStatus.OK);
    }

    private void hasErrorDto(BindingResult bindingResult) throws BadRequestException {
        if(bindingResult.hasErrors()) {
            List<String> list = new ArrayList<>();
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                String defaultMessage = objectError.getDefaultMessage();
                list.add(defaultMessage);
            }
            throw new BadRequestException(list.toString());
        }
    }
}
