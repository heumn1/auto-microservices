package ru.heumn.storageservice.factories;

import org.springframework.stereotype.Component;
import ru.heumn.storageservice.storages.dto.SupplierDtoRequest;
import ru.heumn.storageservice.storages.dto.SupplierDtoResponse;
import ru.heumn.storageservice.storages.entity.SupplierEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SupplierDtoFactory {

    public static SupplierDtoResponse makeDtoSupplier(SupplierEntity supplier){
        return SupplierDtoResponse.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .email(supplier.getEmail())
                .phone(supplier.getPhone())
                .build();
    }

    public static List<SupplierDtoResponse> makeDtoListSupplier(List<SupplierEntity> list) {
        return list.stream().map(SupplierDtoFactory::makeDtoSupplier).collect(Collectors.toList());
    }

    public static SupplierEntity makeEntitySupplier(SupplierDtoRequest supplier){
        return SupplierEntity.builder()
                .name(supplier.getName())
                .email(supplier.getEmail())
                .phone(supplier.getPhone())
                .build();
    }
}
