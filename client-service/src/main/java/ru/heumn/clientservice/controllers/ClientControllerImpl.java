package ru.heumn.clientservice.controllers;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.heumn.clientservice.service.ClientServiceImpl;
import ru.heumn.clientservice.storages.dto.ClientDtoRequest;
import ru.heumn.clientservice.storages.dto.ClientDtoResponse;
import ru.heumn.clientservice.storages.dto.OrderDtoResponse;
import ru.heumn.clientservice.storages.exceptions.BadRequestException;
import ru.heumn.clientservice.storages.exceptions.ConflictRequestException;
import ru.heumn.clientservice.storages.exceptions.NotFoundException;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api/client/")
public class ClientControllerImpl implements ClientController{

    @Autowired
    ClientServiceImpl clientService;

    @GetMapping()
    public ResponseEntity<List<ClientDtoResponse>> getAllClient(){
        return new ResponseEntity<>(clientService.getAllClients(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDtoResponse> getAllClient(@PathVariable Long id) throws NotFoundException, BadRequestException {
        if(id <= 0) {
            throw new BadRequestException("bad id");
        }
        return new ResponseEntity<>(clientService.getClientById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ClientDtoResponse> addClient(@RequestBody @Valid ClientDtoRequest clientDtoRequest){
        return new ResponseEntity<>(clientService.addClient(clientDtoRequest), HttpStatus.OK) ;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientDtoResponse> updateClient(@PathVariable Long id, @RequestBody @Valid ClientDtoRequest clientDtoRequest)
            throws NotFoundException, BadRequestException {
        if(id <= 0){
            throw new BadRequestException("bad id");
        }
        return new ResponseEntity<>(clientService.updateClient(id, clientDtoRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteClient(@PathVariable Long id) throws NotFoundException, BadRequestException {
        if(id <= 0){
            throw new BadRequestException("bad id");
        }
        return new ResponseEntity<>(clientService.deleteClient(id), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderDtoResponse>> getAllOrdersOfClient(@PathVariable Long id) throws BadRequestException, NotFoundException {
        if(id <= 0){
            throw new BadRequestException("bad id");
        }
        return new ResponseEntity<>(clientService.getOrdersByClient(id), HttpStatus.OK);
    }

    @PostMapping("/{numberClient}/orders/{idOrder}/number")
    public ResponseEntity<ClientDtoResponse> addOrderToClientByNumber(@PathVariable String numberClient, @PathVariable Long idOrder) throws BadRequestException, ConflictRequestException, NotFoundException {
        if(idOrder <= 0){
            throw new BadRequestException("bad id");
        }
        return new ResponseEntity<>(clientService.addOrderToClientByNumber(numberClient, idOrder), HttpStatus.OK);
    }

    @PostMapping("/{idClient}/orders/{idOrder}")
    public ResponseEntity<ClientDtoResponse> addOrderToClient(@PathVariable Long idClient, @PathVariable Long idOrder) throws BadRequestException, ConflictRequestException, NotFoundException {
        if(idClient <= 0 || idOrder <= 0){
            throw new BadRequestException("bad id");
        }
        return new ResponseEntity<>(clientService.addOrderToClient(idClient, idOrder), HttpStatus.OK);
    }

    @DeleteMapping("/{idClient}/orders/{idOrder}")
    public ResponseEntity<ClientDtoResponse> deleteOrderOfClient(@PathVariable Long idClient, @PathVariable Long idOrder) throws BadRequestException, NotFoundException {
        if(idClient <= 0 || idOrder <= 0){
            throw new BadRequestException("bad id");
        }
        return new ResponseEntity<>(clientService.deleteOrdersByClient(idClient, idOrder), HttpStatus.OK);
    }
}
