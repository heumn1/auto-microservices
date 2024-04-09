package ru.heumn.storageservice.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.heumn.storageservice.service.PartServiceImpl;
import ru.heumn.storageservice.storages.dto.*;
import ru.heumn.storageservice.storages.exceptions.BadRequestException;
import ru.heumn.storageservice.storages.exceptions.ConflictRequestException;
import ru.heumn.storageservice.storages.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/storage/part")
public class PartController {

    @Autowired
    PartServiceImpl partServiceImpl;


    @PatchMapping("/{id}/augment")
    public ResponseEntity<Boolean> augmentPart(@PathVariable(name = "id") Long id)
            throws BadRequestException, NotFoundException {
        return new ResponseEntity<>(partServiceImpl.augmentPart(id), HttpStatus.OK);
    }


    @PatchMapping("/{id}/deduct")
    public ResponseEntity<Boolean> deductPart(@PathVariable(name = "id") Long id)
            throws BadRequestException, NotFoundException {
        return new ResponseEntity<>(partServiceImpl.deductPart(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Boolean> addPart(@RequestBody @Valid PartDtoRequest partDtoRequest, BindingResult bindingResult)
            throws BadRequestException, ConflictRequestException {
        hasErrorDto(bindingResult);
        return new ResponseEntity<>(partServiceImpl.addPart(partDtoRequest), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<PartDtoResponse>> getAllParts(){
        return new ResponseEntity<>(partServiceImpl.getAllParts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartDtoResponse> getPart(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(partServiceImpl.getPart(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePart(@PathVariable Long id) throws NotFoundException, BadRequestException {
        return new ResponseEntity<>(partServiceImpl.deletePart(id), HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PartDtoResponse> updatePart(@RequestBody @Valid PartDtoRequest partDtoRequest, @PathVariable Long id, BindingResult bindingResult) throws NotFoundException, BadRequestException {
        hasErrorDto(bindingResult);
        return new ResponseEntity<>(partServiceImpl.update(id, partDtoRequest), HttpStatus.OK);
    }

    @GetMapping("/{id}/supplier")
    public ResponseEntity<List<PriceDtoResponse>> getAllSupplierByPart(@PathVariable Long id) throws BadRequestException, NotFoundException {
        return new ResponseEntity<>(partServiceImpl.getAllSupplierByPart(id), HttpStatus.OK);
    }

    @PostMapping("/supplier")
    public ResponseEntity<PriceDtoResponse> addPartToSupplier(@RequestBody @Valid PriceDtoRequest priceDtoRequest, BindingResult bindingResult)
            throws BadRequestException, NotFoundException {
        hasErrorDto(bindingResult);
        return new ResponseEntity<>(partServiceImpl.addPartToSupplier(priceDtoRequest), HttpStatus.OK);
    }

    @GetMapping("{id}/is-in-stock")
    public ResponseEntity<Boolean> isInStock(@PathVariable Long id) throws BadRequestException, NotFoundException {
        return new ResponseEntity<>(partServiceImpl.isInStock(id), HttpStatus.OK);
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
