package ru.heumn.clientservice.storages.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.heumn.clientservice.storages.Status;
import ru.heumn.clientservice.storages.entity.PartEntity;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDtoResponse {
    Long id;
    String type;
    String brandCar;
    String numberCar;
    String numberClient;
    Double price;
    Status status;
    List<PartEntity> parts;
}
