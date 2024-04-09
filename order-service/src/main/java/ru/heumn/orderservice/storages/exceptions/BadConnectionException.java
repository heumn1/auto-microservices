package ru.heumn.orderservice.storages.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
public class BadConnectionException extends Exception {
    public BadConnectionException(String message) {
        super(message);
    }
}
