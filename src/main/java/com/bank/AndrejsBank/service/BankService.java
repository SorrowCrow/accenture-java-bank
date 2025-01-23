package com.bank.AndrejsBank.service;

import com.bank.AndrejsBank.documents.BankAccount;
import com.bank.AndrejsBank.exception.EntityNotFoundException;
import com.bank.AndrejsBank.interfaces.BankAccountRepository;
import com.bank.AndrejsBank.model.request.BankAccountCreationRequest;
import com.bank.AndrejsBank.model.request.BankAccountUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankService {
    private final BankAccountRepository bankAccountRepository;

    public BankAccount readBankAccountById(String id) {
        Optional<BankAccount> bankAccount = bankAccountRepository.findById(id);
        if (bankAccount.isPresent()) {
            return bankAccount.get();
        }
        throw new EntityNotFoundException("Cant find bank account");
    }

    public List<BankAccount> readBankAccounts() {
        return bankAccountRepository.findAll();
    }

    public BankAccount readBankAccount(String name) {
        Optional<BankAccount> bankAccount = bankAccountRepository.findByName(name);
        if (bankAccount.isPresent()) {
            return bankAccount.get();
        }
        throw new EntityNotFoundException("Cant find any bank account under given name");
    }

    public BankAccount createBankAccount(BankAccountCreationRequest bankAccount) {
        BankAccount bankAccountToCreate = new BankAccount();
        BeanUtils.copyProperties(bankAccount, bankAccountToCreate);
        return bankAccountRepository.save(bankAccountToCreate);
    }

    public BankAccount updateBankAccount(String name, BankAccountUpdateRequest request) {
//        if(amount<0){
//            throw new RuntimeException("Amount can not be less than 0");
//        }
        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findByName(name);
        if (!optionalBankAccount.isPresent()) {
            throw new EntityNotFoundException("Cant find any BankAccount under given name");
        }

        BankAccount bankAccount = optionalBankAccount.get();

        bankAccount.setBalance(bankAccount.getBalance() + request.getAmount());

        return bankAccountRepository.save(bankAccount);
    }

    public void deleteBankAccount(String id) {
        bankAccountRepository.deleteById(id);
    }
}
