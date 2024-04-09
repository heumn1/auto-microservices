package ru.heumn.storageservice.storages.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
public class PriceDtoRequest {

    @NotNull(message = "supplierId cannot be null")
    Long supplierId;

    @NotNull(message = "partId cannot be null")
    Long partId;

    @NotNull(message = "price cannot be null")
    @Min(value = 1, message = "price cannot be less than 0")
    Double price;
}
