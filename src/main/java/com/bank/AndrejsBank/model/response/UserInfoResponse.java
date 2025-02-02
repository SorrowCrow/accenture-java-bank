package com.bank.AndrejsBank.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserInfoResponse {
    private int id;
    private String username;
    private List<String> roles;
}
