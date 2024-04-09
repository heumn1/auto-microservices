package ru.heumn.clientservice.storages.event;


import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderCreatedEvent {

    Long orderId;

    String clientNumber;
}
