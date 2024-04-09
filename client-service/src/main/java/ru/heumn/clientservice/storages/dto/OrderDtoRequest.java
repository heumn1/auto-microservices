package ru.heumn.clientservice.storages.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.heumn.clientservice.storages.Status;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDtoRequest {

    @NotNull(message = "there is no required field \"type\"")
    @NotEmpty(message = "the field \"type\" should not be empty")
    @Size(min = 2, max = 99,  message = "the minimum value of the field \"type\" is 2")
    String type;

    @NotNull(message = "there is no required field \"brandCar\"")
    @NotEmpty(message = "the field \"brandCar\" should not be empty")
    @Size(min = 2, max = 99,  message = "the minimum value of the field \"brandCar\" is 2")
    String brandCar;

    @NotNull(message = "there is no required field \"numberCar\"")
    @NotEmpty(message = "the field \"numberCar\" should not be empty")
    @Size(min = 6, max = 99,  message = "the minimum value of the field \"numberCar\" is 6")
    String numberCar;

    @NotNull(message = "there is no required field \"numberClient\"")
    @NotEmpty(message = "the field \"numberClient\" should not be empty")
    @Size(min = 6, max = 99,  message = "the minimum value of the field \"numberClient\" is 6")
    String numberClient;

    @NotNull(message = "there is no required field \"status\"")
    @NotEmpty(message = "the field \"status\" should not be empty")
    Status status;
}
