package com.bank.AndrejsBank.model.request.bankAccount;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountRequest {
    private BigDecimal amount;
    //  account that is modified
    private String toAccountNumber;

    //  account from which is modified
    private String fromAccountNumber;
}
