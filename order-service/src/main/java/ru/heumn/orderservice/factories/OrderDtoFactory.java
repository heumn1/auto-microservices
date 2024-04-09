package ru.heumn.orderservice.factories;

import org.springframework.stereotype.Component;
import ru.heumn.orderservice.storages.dto.OrderDtoRequest;
import ru.heumn.orderservice.storages.dto.OrderDtoResponse;
import ru.heumn.orderservice.storages.entity.OrderEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderDtoFactory {

    public static OrderDtoResponse makeDtoOrderResponse(OrderEntity order){
        return OrderDtoResponse.builder()
                .id(order.getId())
                .brandCar(order.getBrandCar())
                .numberCar(order.getNumberCar())
                .status(order.getStatus())
                .numberClient(order.getNumberClient())
                .type(order.getType())
                .parts(order.getParts())
                .price(order.getPrice())
                .build();
    }

    public static List<OrderDtoResponse> makeDtoListOrder(List<OrderEntity> list) {

        return list.stream().map(OrderDtoFactory::makeDtoOrderResponse).collect(Collectors.toList());
    }

    public static OrderEntity makeOrderEntity(OrderDtoRequest order){
        return OrderEntity.builder()
                .brandCar(order.getBrandCar())
                .numberCar(order.getNumberCar())
                .status(order.getStatus())
                .numberClient(order.getNumberClient())
                .type(order.getType())
                .build();
    }


}
