package ru.heumn.storageservice.storages.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartDtoResponse {
    Long id;
    String name;
    String description;
    String category;
    Integer count;
    Double price;
}
