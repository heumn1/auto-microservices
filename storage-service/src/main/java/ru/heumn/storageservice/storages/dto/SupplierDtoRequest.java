package ru.heumn.storageservice.storages.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDtoRequest {

    @NotNull(message = "name it cannot be null")
    String name;

    String email;
    String phone;
}
