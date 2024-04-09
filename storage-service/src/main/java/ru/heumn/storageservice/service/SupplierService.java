package ru.heumn.storageservice.service;

import ru.heumn.storageservice.storages.dto.PriceDtoResponse;
import ru.heumn.storageservice.storages.dto.SupplierDtoRequest;
import ru.heumn.storageservice.storages.dto.SupplierDtoResponse;
import ru.heumn.storageservice.storages.exceptions.BadRequestException;
import ru.heumn.storageservice.storages.exceptions.NotFoundException;

import java.util.List;

public interface SupplierService {
    Boolean addSupplier(SupplierDtoRequest supplierDtoRequest);
    List<SupplierDtoResponse> getAllSupplier();
    SupplierDtoResponse getSupplierById(Long id) throws NotFoundException ;
    SupplierDtoResponse updateSupplier(Long id, SupplierDtoRequest supplierDtoRequest) throws BadRequestException, NotFoundException;
    Boolean deleteSupplier(Long id) throws BadRequestException, NotFoundException;
    List<PriceDtoResponse> getAllPathBySeller(Long id) throws BadRequestException, NotFoundException;
}
