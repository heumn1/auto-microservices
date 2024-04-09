package ru.heumn.clientservice.factories;

import ru.heumn.clientservice.storages.dto.ClientDtoRequest;
import ru.heumn.clientservice.storages.dto.ClientDtoResponse;
import ru.heumn.clientservice.storages.dto.OrderDtoRequest;
import ru.heumn.clientservice.storages.dto.OrderDtoResponse;
import ru.heumn.clientservice.storages.entity.ClientEntity;
import ru.heumn.clientservice.storages.entity.OrderEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ClientDtoFactory {

    public static ClientDtoResponse makeClientDtoResponse(ClientEntity client){
        return ClientDtoResponse.builder()
                .id(client.getId())
                .name(client.getName())
                .email(client.getEmail())
                .lastname(client.getLastname())
                .patronymic(client.getPatronymic())
                .number(client.getNumber())
                .orders(client.getOrders())
                .build();
    }

    public static List<ClientDtoResponse> makeDtoListClient(List<ClientEntity> list) {

        return list.stream().map(ClientDtoFactory::makeClientDtoResponse).collect(Collectors.toList());
    }

    public static ClientEntity makeClientEntity(ClientDtoRequest client){
        return ClientEntity.builder()
                .name(client.getName())
                .email(client.getEmail())
                .lastname(client.getLastname())
                .patronymic(client.getPatronymic())
                .number(client.getNumber())
                .build();
    }


}
