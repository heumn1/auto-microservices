package ru.heumn.storageservice.storages.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.heumn.storageservice.storages.entity.PartEntity;
import ru.heumn.storageservice.storages.entity.SupplierEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceDtoResponse {
    Long id;
    SupplierEntity supplier;
    PartEntity part;
    Double price;
}
