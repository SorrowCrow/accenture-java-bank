package com.bank.AndrejsBank.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {
//    private static final long serialVersionUID = 1L;
    private String message;
    public EntityNotFoundException( String message) {
        super(message);
    }
}
