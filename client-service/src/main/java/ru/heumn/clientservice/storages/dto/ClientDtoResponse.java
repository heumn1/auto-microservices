package ru.heumn.clientservice.storages.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.heumn.clientservice.storages.entity.OrderEntity;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDtoResponse {

    Long id;

    String name;

    String lastname;

    String patronymic;

    String number;

    String email;

    Set<OrderEntity> orders = new HashSet<>();
}
