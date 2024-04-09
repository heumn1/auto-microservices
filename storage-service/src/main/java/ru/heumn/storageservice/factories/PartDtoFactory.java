package ru.heumn.storageservice.factories;

import org.springframework.stereotype.Component;
import ru.heumn.storageservice.storages.dto.PartDtoRequest;
import ru.heumn.storageservice.storages.dto.PartDtoResponse;
import ru.heumn.storageservice.storages.entity.PartEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PartDtoFactory {


    public static PartDtoResponse makeDtoPartResponse(PartEntity part){
        return PartDtoResponse.builder()
                .id(part.getId())
                .category(part.getCategory())
                .price(part.getPrice())
                .count(part.getCount())
                .description(part.getDescription())
                .name(part.getName())
                .build();
    }

    public static List<PartDtoResponse> makeDtoListPart(List<PartEntity> list) {
        return list.stream().map(PartDtoFactory::makeDtoPartResponse).collect(Collectors.toList());
    }

    public static PartEntity makeEntityPart(PartDtoRequest part){
        return PartEntity.builder()
                .category(part.getCategory())
                .count(part.getCount())
                .price(part.getPrice())
                .description(part.getDescription())
                .name(part.getName())
                .build();
    }


}
