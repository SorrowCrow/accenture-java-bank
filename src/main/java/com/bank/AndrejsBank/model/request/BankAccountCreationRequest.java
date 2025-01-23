package com.bank.AndrejsBank.model.request;

import lombok.Data;

@Data
public class BankAccountCreationRequest {
    private String name;
    private double balance;
}
