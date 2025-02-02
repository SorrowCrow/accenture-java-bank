package com.bank.AndrejsBank.model.request.bankAccount;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class BankAccountTransferRequest extends  BankAccountRequest {
    private BigDecimal amount;
    private String fromAccountNumber;
    private String toAccountNumber;
    private String apiUrl;
}
