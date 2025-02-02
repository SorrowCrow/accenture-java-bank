package com.bank.AndrejsBank.model.request;

import lombok.*;

@Data
public class AuthenticationRequest {
    private String username;
    private String password;
}
