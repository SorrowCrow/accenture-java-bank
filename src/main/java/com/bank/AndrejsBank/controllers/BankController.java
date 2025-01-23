package com.bank.AndrejsBank.controllers;

import com.bank.AndrejsBank.documents.BankAccount;
import com.bank.AndrejsBank.model.request.BankAccountCreationRequest;
import com.bank.AndrejsBank.model.request.BankAccountUpdateRequest;
import com.bank.AndrejsBank.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/bank")
@RequiredArgsConstructor
public class BankController {
    private final BankService bankService;

    @GetMapping("/account")
    public ResponseEntity readBankAccounts(@RequestParam(required = false) String name) {
        if (name == null) {
            return ResponseEntity.ok(bankService.readBankAccounts());
        }
        return ResponseEntity.ok(bankService.readBankAccount(name));
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<BankAccount> readBankAccount(@PathVariable String accountId) {
        return ResponseEntity.ok(bankService.readBankAccountById(accountId));
    }

    @PostMapping("/account")
    public ResponseEntity<BankAccount> createBankAccount(@RequestBody BankAccountCreationRequest request) {
        return ResponseEntity.ok(bankService.createBankAccount(request));
    }

    @PatchMapping("/account/{name}")
    public ResponseEntity<BankAccount> createBankAccount(@RequestBody BankAccountUpdateRequest request, @PathVariable String name) {
        return ResponseEntity.ok(bankService.updateBankAccount(name, request));
    }

    @DeleteMapping("/account/{accountId}")
    public ResponseEntity<Void> deleteBankAccount(@PathVariable String accountId) {
        bankService.deleteBankAccount(accountId);
        return ResponseEntity.ok().build();
    }
}
