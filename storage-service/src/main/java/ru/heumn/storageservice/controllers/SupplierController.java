package ru.heumn.storageservice.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.heumn.storageservice.service.SupplierServiceImpl;
import ru.heumn.storageservice.storages.dto.PriceDtoResponse;
import ru.heumn.storageservice.storages.dto.SupplierDtoRequest;
import ru.heumn.storageservice.storages.dto.SupplierDtoResponse;
import ru.heumn.storageservice.storages.exceptions.BadRequestException;
import ru.heumn.storageservice.storages.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/storage/supplier")
public class SupplierController {

    @Autowired
    SupplierServiceImpl supplierServiceImpl;

    @PostMapping("/")
    public ResponseEntity<Boolean> addSupplier(@RequestBody @Valid SupplierDtoRequest supplierDtoRequest, BindingResult bindingResult) throws BadRequestException {
        hasErrorDto(bindingResult);
        return new ResponseEntity<>(supplierServiceImpl.addSupplier(supplierDtoRequest), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<SupplierDtoResponse>> getAllSupplier(){
        return new ResponseEntity<>(supplierServiceImpl.getAllSupplier(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDtoResponse> getPart(@PathVariable Long id)
            throws NotFoundException {
        return new ResponseEntity<>(supplierServiceImpl.getSupplierById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePart(@PathVariable Long id)
            throws NotFoundException, BadRequestException {
        return new ResponseEntity<>(supplierServiceImpl.deleteSupplier(id), HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SupplierDtoResponse> updatePart(@RequestBody @Valid SupplierDtoRequest supplierDtoRequest, @PathVariable Long id, BindingResult bindingResult)
            throws NotFoundException, BadRequestException {
        hasErrorDto(bindingResult);
        return new ResponseEntity<>(supplierServiceImpl.updateSupplier(id, supplierDtoRequest), HttpStatus.OK);
    }

    @GetMapping("/{id}/part")
    public ResponseEntity<List<PriceDtoResponse>> getPartBySupplier(@PathVariable Long id) throws BadRequestException, NotFoundException {
        return new ResponseEntity<>(supplierServiceImpl.getAllPathBySeller(id), HttpStatus.OK);
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
