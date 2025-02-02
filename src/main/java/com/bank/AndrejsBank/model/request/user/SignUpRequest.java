package com.bank.AndrejsBank.model.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)

//    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;


    @NotBlank
    @Size(min = 6, max = 40)
    private String name;


    @NotBlank
    @Size(min = 6, max = 40)
    private String surname;
}