package com.bank.AndrejsBank.services;

import com.bank.AndrejsBank.documents.BankAccount;
import com.bank.AndrejsBank.exception.EntityNotFoundException;
import com.bank.AndrejsBank.interfaces.BankAccountRepository;
import com.bank.AndrejsBank.model.request.bankAccount.BankAccountRequest;
import com.bank.AndrejsBank.model.request.bankAccount.BankAccountTransferRequest;
import com.bank.AndrejsBank.model.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankAccountService implements IBankAccount {
    private final BankAccountRepository bankAccountRepository;

    private final TransactionService transactionService;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public BankAccount findById(String id) {
        Optional<BankAccount> bankAccount = bankAccountRepository.findById(id);
        if (bankAccount.isPresent()) {
            return bankAccount.get();
        }
        throw new EntityNotFoundException("Cant find bank account");
    }

    public List<BankAccount> findAll() {
        return bankAccountRepository.findAll();
    }

    public BankAccount save(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    public BankAccount withdraw(BankAccountRequest bankRequest) {
        if (bankRequest.getAmount().signum() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Withdraw cant be less or equal to zero");
        }

        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(bankRequest.getToAccountNumber());
        if (!optionalBankAccount.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cant find any BankAccount under given id");
        }

        BankAccount bankAccount = optionalBankAccount.get();

        if (bankAccount.getBalance().compareTo(bankRequest.getAmount()) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough money");
        }


        if (bankRequest.getFromAccountNumber() == null) {
            transactionService.addTransaction(bankAccount, bankRequest.getAmount().negate());
        } else {
            transactionService.addTransaction(bankAccount, bankRequest.getAmount().negate(), bankRequest.getFromAccountNumber());
        }

        return bankAccountRepository.save(bankAccount);
    }

    public BankAccount deposit(BankAccountRequest bankRequest) {
        if (bankRequest.getAmount().signum() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deposit cant be less or equal to zero");
        }
        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(bankRequest.getToAccountNumber());
        if (!optionalBankAccount.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cant find any BankAccount under given id");
        }

        BankAccount bankAccount = optionalBankAccount.get();

        if (bankRequest.getFromAccountNumber() == null) {
            transactionService.addTransaction(bankAccount, bankRequest.getAmount());
        } else {
            transactionService.addTransaction(bankAccount, bankRequest.getAmount(), bankRequest.getFromAccountNumber());
//            bankAccount.deposit(bankRequest.getFromAccountNumber(), bankRequest.getAmount());
        }

        return bankAccountRepository.save(bankAccount);
    }

    public ResponseEntity<?> transfer(BankAccountTransferRequest request) {

        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(request.getFromAccountNumber());

        Optional<BankAccount> optionalToBankAccount = bankAccountRepository.findById(request.getToAccountNumber());

        if (!optionalBankAccount.isPresent()) {
            return ResponseEntity.internalServerError().body(new MessageResponse("No such fromAccount id"));
        }

        if (!(request.getApiUrl() == null)) {
            HttpHeaders headers = new HttpHeaders();
            // set `content-type` header
            headers.setContentType(MediaType.APPLICATION_JSON);
            // set `accept` header
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            Map<String, Object> map = new HashMap<>();
            map.put("amount", request.getAmount());
            map.put("fromAccountNumber", request.getFromAccountNumber());
            map.put("toAccountNumber", request.getToAccountNumber());

            // build the request
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

            // send the request
            ResponseEntity<?> response = restTemplate().postForEntity(request.getApiUrl(), entity, BankAccount.class);

            if (response.getStatusCode().isError()) {
                return ResponseEntity.internalServerError().body(new MessageResponse("Transfer response returned error"));
            }

            BankAccountTransferRequest swap = new BankAccountTransferRequest();

            swap.setToAccountNumber(request.getFromAccountNumber());
            swap.setFromAccountNumber(request.getToAccountNumber());
            swap.setAmount(request.getAmount());

            withdraw(swap);
        } else {
            if (!optionalToBankAccount.isPresent()) {
                return ResponseEntity.internalServerError().body(new MessageResponse("No such toAccount id"));
            }

            BankAccountTransferRequest swap = new BankAccountTransferRequest();

            swap.setToAccountNumber(request.getFromAccountNumber());
            swap.setFromAccountNumber(request.getToAccountNumber());
            swap.setAmount(request.getAmount());

            withdraw(swap);

            deposit(request);
        }

//        HashMap<String, String> map = new HashMap<>();
//        map.put("message", "Success");

        return ResponseEntity.ok( new MessageResponse("Success"));
    }

    public ResponseEntity transferExternal(BankAccountTransferRequest request) {
        if (request.getAmount().compareTo(BigDecimal.ZERO)<=0) {
            return ResponseEntity.internalServerError().body(new MessageResponse("Value can not be less or equal to 0"));
        }

        Optional<BankAccount> optionalToBankAccopunt = bankAccountRepository.findById(request.getToAccountNumber());

        if (!optionalToBankAccopunt.isPresent()) {
            return ResponseEntity.internalServerError().body(new MessageResponse("No such toAccount id"));
        }


        BankAccount toBankAccount = optionalToBankAccopunt.get();

        transactionService.addTransaction(toBankAccount, request.getAmount(), request.getFromAccountNumber());

        bankAccountRepository.save(toBankAccount);

        return ResponseEntity.ok(new MessageResponse("Success"));
    }

    public void deleteById(String id) {
        bankAccountRepository.deleteById(id);
    }
}
