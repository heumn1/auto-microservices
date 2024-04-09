package ru.heumn.clientservice.storages.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDtoRequest {

    @NotNull(message = "there is no required field \"name\"")
    @NotEmpty(message = "the field \"name\" should not be empty")
    @Size(min = 2, max = 99,  message = "the minimum value of the field \"name\" is 2")
    String name;

    @NotNull(message = "there is no required field \"lastname\"")
    @NotEmpty(message = "the field \"lastname\" should not be empty")
    @Size(min = 2, max = 99,  message = "the minimum value of the field \"lastname\" is 2")
    String lastname;

    String patronymic;

    @NotNull(message = "there is no required field \"number\"")
    @NotEmpty(message = "the field \"number\" should not be empty")
    String number;

    String email;
}
