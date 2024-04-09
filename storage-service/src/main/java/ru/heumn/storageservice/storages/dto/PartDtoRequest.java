package ru.heumn.storageservice.storages.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartDtoRequest {
    @NotNull(message = "name it cannot be null")
    String name;

    String description;

    @NotNull(message = "category it cannot be null")
    String category;

    @NotNull(message = "count it cannot be null")
    @Min(value = 0, message = "count cannot be less than 0")
    Integer count;

    @NotNull(message = "price it cannot be null")
    @Min(value = 0, message = "price cannot be less than 0")
    Double price;
}
