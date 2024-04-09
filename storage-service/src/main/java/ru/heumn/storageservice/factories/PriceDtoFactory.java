package ru.heumn.storageservice.factories;

import org.springframework.stereotype.Component;
import ru.heumn.storageservice.storages.dto.PriceDtoRequest;
import ru.heumn.storageservice.storages.dto.PriceDtoResponse;
import ru.heumn.storageservice.storages.entity.PriceEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PriceDtoFactory {

    public static PriceDtoResponse makeDtoPriceResponse(PriceEntity price){
        return PriceDtoResponse.builder()
                .id(price.getId())
                .part(price.getPart())
                .supplier(price.getSupplier())
                .price(price.getPrice())
                .build();
    }

    public static List<PriceDtoResponse> makeDtoListPrice(List<PriceEntity> list) {
        return list.stream().map(PriceDtoFactory::makeDtoPriceResponse).collect(Collectors.toList());
    }
}
