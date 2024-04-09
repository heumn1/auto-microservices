package ru.heumn.orderservice.storages.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.heumn.orderservice.storages.Status;
import ru.heumn.orderservice.storages.entity.PartEntity;

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
