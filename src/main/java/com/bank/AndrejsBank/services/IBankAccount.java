package com.bank.AndrejsBank.services;

import com.bank.AndrejsBank.documents.BankAccount;

import java.util.List;
import java.util.Optional;

public interface IBankAccount {
    List<BankAccount> findAll();
    BankAccount findById(String id);
    BankAccount save(BankAccount bkac);
    void deleteById(String id);
}
