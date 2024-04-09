package ru.heumn.storageservice.service;

import lombok.extern.log4j.Log4j2;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import ru.heumn.storageservice.factories.PriceDtoFactory;
import ru.heumn.storageservice.factories.SupplierDtoFactory;
import ru.heumn.storageservice.storages.dto.PriceDtoResponse;
import ru.heumn.storageservice.storages.dto.SupplierDtoRequest;
import ru.heumn.storageservice.storages.dto.SupplierDtoResponse;
import ru.heumn.storageservice.storages.entity.SupplierEntity;
import ru.heumn.storageservice.storages.exceptions.BadRequestException;
import ru.heumn.storageservice.storages.exceptions.NotFoundException;
import ru.heumn.storageservice.storages.repository.PriceRepository;
import ru.heumn.storageservice.storages.repository.SupplierRepository;
import ru.heumn.storageservice.util.HibernateUtil;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class SupplierServiceImpl implements SupplierService{

    SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
    SupplierRepository supplierRepository = new SupplierRepository(sessionFactory);

    PriceRepository priceRepository = new PriceRepository(sessionFactory);

    public Boolean addSupplier(SupplierDtoRequest supplierDtoRequest) {
        log.info("triggering method: addSupplier with object: " + supplierDtoRequest);

        SupplierEntity supplier = SupplierDtoFactory.makeEntitySupplier(supplierDtoRequest);
        supplierRepository.save(supplier);
        return true;
    }

    public List<SupplierDtoResponse> getAllSupplier() {
        log.info("triggering method: getAllSupplier");

        List<SupplierEntity> supplierEntities = supplierRepository.findAll();
        return SupplierDtoFactory.makeDtoListSupplier(supplierEntities);
    }

    public SupplierDtoResponse getSupplierById(Long id) throws NotFoundException {
        log.info("triggering method: getSupplierById with id: " + id);

        SupplierEntity supplier = supplierRepository.findById(id).orElseThrow(() -> new NotFoundException("Supplier not found"));
        return SupplierDtoFactory.makeDtoSupplier(supplier);
    }

    public SupplierDtoResponse updateSupplier(Long id, SupplierDtoRequest supplierDtoRequest) throws BadRequestException, NotFoundException {
        log.info("triggering method: updateSupplier with id: " + id + " and object: " + supplierDtoRequest);

        if(id <= 0) {
            throw new BadRequestException("bad id");
        }
        else if(supplierRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Supplier not found");
        }
        else {
            SupplierEntity supplier = SupplierDtoFactory.makeEntitySupplier(supplierDtoRequest);
            supplier.setId(id);
            supplierRepository.update(supplier);
            return SupplierDtoFactory.makeDtoSupplier(supplier);
        }
    }

    public Boolean deleteSupplier(Long id) throws BadRequestException, NotFoundException {
        log.info("triggering method: deleteSupplier with id: " + id);

        if(id <= 0) {
            throw new BadRequestException("bad id");
        }
        else {
            Optional<SupplierEntity> supplier = supplierRepository.findById(id);
            if(supplier.isEmpty()) {
                throw new NotFoundException("Supplier not found");
            }
            else {
                supplierRepository.delete(supplier.get());
                return true;
            }
        }
    }

    public List<PriceDtoResponse> getAllPathBySeller(Long id) throws BadRequestException, NotFoundException {
        log.info("triggering method: getAllPathBySeller with id: " + id);

        if(id < 0) {
            throw new BadRequestException("bad id");
        }

        SupplierEntity supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Supplier not found"));


        return PriceDtoFactory.makeDtoListPrice(priceRepository.getPartBySupplier(supplier));
    }
}
