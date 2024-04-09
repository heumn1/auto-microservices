package ru.heumn.clientservice.storages.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictRequestException extends Exception{

    public ConflictRequestException(String message){
        super(message);
    }
}
