package ru.heumn.storageservice.service;

import ru.heumn.storageservice.storages.dto.PartDtoRequest;
import ru.heumn.storageservice.storages.dto.PartDtoResponse;
import ru.heumn.storageservice.storages.dto.PriceDtoRequest;
import ru.heumn.storageservice.storages.dto.PriceDtoResponse;
import ru.heumn.storageservice.storages.exceptions.BadRequestException;
import ru.heumn.storageservice.storages.exceptions.NotFoundException;

import java.util.List;

public interface PartService {
    List<PartDtoResponse> getAllParts();
    Boolean deletePart(Long id) throws NotFoundException, BadRequestException;
    PartDtoResponse getPart(Long id) throws NotFoundException;
    PartDtoResponse update(Long id, PartDtoRequest partDtoRequest) throws NotFoundException, BadRequestException;
    PriceDtoResponse addPartToSupplier(PriceDtoRequest priceDtoRequest) throws BadRequestException, NotFoundException;
    List<PriceDtoResponse> getAllSupplierByPart(Long id) throws BadRequestException, NotFoundException;
    Boolean isInStock(Long id) throws BadRequestException, NotFoundException;
    Boolean deductPart(Long idPart) throws BadRequestException, NotFoundException;
    Boolean augmentPart(Long idPart) throws BadRequestException, NotFoundException;
}
