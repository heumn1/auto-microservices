package ru.heumn.storageservice.service;

import lombok.extern.log4j.Log4j2;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import ru.heumn.storageservice.factories.PartDtoFactory;
import ru.heumn.storageservice.factories.PriceDtoFactory;
import ru.heumn.storageservice.storages.dto.*;
import ru.heumn.storageservice.storages.entity.PartEntity;
import ru.heumn.storageservice.storages.entity.PriceEntity;
import ru.heumn.storageservice.storages.entity.SupplierEntity;
import ru.heumn.storageservice.storages.exceptions.BadRequestException;
import ru.heumn.storageservice.storages.exceptions.NotFoundException;
import ru.heumn.storageservice.storages.repository.PartRepository;
import ru.heumn.storageservice.storages.repository.PriceRepository;
import ru.heumn.storageservice.storages.repository.SupplierRepository;
import ru.heumn.storageservice.util.HibernateUtil;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class PartServiceImpl implements PartService {

    SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
    PartRepository partRepository = new PartRepository(sessionFactory);
    SupplierRepository supplierRepository = new SupplierRepository(sessionFactory);
    PriceRepository priceRepository = new PriceRepository(sessionFactory);


    public List<PartDtoResponse> getAllParts(){
        log.info("triggering method: getAllParts");
        return PartDtoFactory.makeDtoListPart(partRepository.findAll());
    }

    public Boolean addPart(PartDtoRequest partDtoRequest) {
        log.info("triggering method: addPart with object: " + partDtoRequest);

        PartEntity part = PartDtoFactory.makeEntityPart(partDtoRequest);
        partRepository.save(part);
        return true;
    }

    public Boolean deletePart(Long id) throws NotFoundException, BadRequestException {
        log.info("triggering method: deletePart with id: " + id);


        if(id <= 0) {
            throw new BadRequestException("bad id");
        }
        else {
            Optional<PartEntity> part = partRepository.findById(id);
            if(part.isEmpty()) {
                throw new NotFoundException("part not found");
            }
            else {
                partRepository.delete(part.get());
                return true;
            }
        }
    }

    public PartDtoResponse getPart(Long id) throws NotFoundException {
        log.info("triggering method: getPart with id: " + id);

        PartEntity part = partRepository.findById(id).orElseThrow(() -> new NotFoundException("part not found"));
        return PartDtoFactory.makeDtoPartResponse(part);
    }

    public PartDtoResponse update(Long id, PartDtoRequest partDtoRequest) throws NotFoundException, BadRequestException {
        log.info("triggering method: update with id: " + id + " and object: " + partDtoRequest);

        PartEntity part = PartDtoFactory.makeEntityPart(partDtoRequest);
        if(id <= 0) {
            throw new BadRequestException("bad id");
        }
        if(partRepository.findById(id).isEmpty()) {
            throw new NotFoundException("part not found");
        }
        else {
            part.setId(id);
            partRepository.update(part);
            part = partRepository.findById(part.getId()).orElseThrow(() -> new NotFoundException("part not found"));
            return PartDtoFactory.makeDtoPartResponse(part);
        }
    }

    public PriceDtoResponse addPartToSupplier(PriceDtoRequest priceDtoRequest) throws BadRequestException, NotFoundException {
        log.info("triggering method: addPartToSupplier with object: " + priceDtoRequest);

        if (priceDtoRequest.getPartId() == null || priceDtoRequest.getPartId() <= 0 ||
                priceDtoRequest.getSupplierId() == null || priceDtoRequest.getSupplierId() <= 0) {
            throw new BadRequestException("Error in PartId or SupplierId");
        }

        SupplierEntity supplier = supplierRepository.findById(priceDtoRequest.getSupplierId())
                .orElseThrow(() -> new NotFoundException("Supplier not found"));

        PartEntity part = partRepository.findById(priceDtoRequest.getPartId())
                .orElseThrow(() -> new NotFoundException("Part not found"));

        if(priceDtoRequest.getPrice() < 0) {
            throw new BadRequestException("price < 0");
        }

        PriceEntity price = PriceEntity.builder()
                .part(part)
                .supplier(supplier)
                .price(priceDtoRequest.getPrice())
                .build();

        priceRepository.save(price);
        return PriceDtoFactory.makeDtoPriceResponse(price);
    }

    public List<PriceDtoResponse> getAllSupplierByPart(Long id) throws BadRequestException, NotFoundException {
        log.info("triggering method: getAllSupplierByPart with id: " + id);

        if(id <= 0) {
            throw new BadRequestException("bad id");
        }

        PartEntity part = partRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Part not found"));

        return PriceDtoFactory.makeDtoListPrice(priceRepository.getSupplierByPart(part));
    }

    public Boolean isInStock(Long id) throws BadRequestException, NotFoundException {
        log.info("triggering method: isInStock with id: " + id);

        if(id <= 0) {
            throw new BadRequestException("bad id");
        }

        PartEntity part = partRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Part not found"));

        return part.getCount() > 0;
    }

    public Boolean deductPart(Long idPart) throws BadRequestException, NotFoundException {
        log.info("triggering method: deductPart with id: " + idPart);

        if (idPart <= 0) {
            throw new BadRequestException("bad id");
        }
        PartEntity part = partRepository.findById(idPart)
                .orElseThrow(() -> new NotFoundException("Part not found"));

        if(part.getCount() <= 0) {
            throw new BadRequestException("The quantity of parts is currently 0");
        }

        part.setCount(part.getCount()-1);
        partRepository.update(part);

        return true;
    }

    public Boolean augmentPart(Long idPart) throws BadRequestException, NotFoundException {
        log.info("triggering method: augmentPart with id: " + idPart);

        if (idPart <= 0) {
            throw new BadRequestException("bad id");
        }
        PartEntity part = partRepository.findById(idPart)
                .orElseThrow(() -> new NotFoundException("Part not found"));

        if(part.getCount() <= 0) {
            throw new BadRequestException("The quantity of parts is currently 0");
        }

        part.setCount(part.getCount()+1);
        partRepository.update(part);

        return true;
    }
}
