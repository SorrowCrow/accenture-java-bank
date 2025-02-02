package com.bank.AndrejsBank.controllers;

import com.bank.AndrejsBank.documents.BankAccount;
import com.bank.AndrejsBank.documents.User;
import com.bank.AndrejsBank.model.request.BadRequest;
import com.bank.AndrejsBank.model.request.bankAccount.BankAccountRequest;
import com.bank.AndrejsBank.model.request.bankAccount.BankAccountTransferRequest;
import com.bank.AndrejsBank.model.request.bankAccount.BankAccountUpdateRequest;
import com.bank.AndrejsBank.services.BankAccountService;
import com.bank.AndrejsBank.services.UserDetailsImpl;
import com.bank.AndrejsBank.services.UserDetailsServiceImpl;
import com.bank.AndrejsBank.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/v1/")
@RequiredArgsConstructor
public class BankController {
    private final BankAccountService bankService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    JwtUtils jwtUtils;

//    @PreAuthorize("isAuthenticated()")
    @GetMapping("/accounts")
    public ResponseEntity<List<UserDetailsImpl>> readBankAccounts() {

            return ResponseEntity.ok(userDetailsService.findAll());

    }

//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/account/{accountId}")
//    public ResponseEntity<BankAccount> readBankAccount(@PathVariable String accountId) {
//        return ResponseEntity.ok(bankService.findById(accountId));
//    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/account")
    public ResponseEntity<?> createBankAccount(HttpServletRequest request) {
//        BankAccount bankAccountToCreate = new BankAccount();
//        BeanUtils.copyProperties(request, bankAccountToCreate);
        String jwt = jwtUtils.getJwtFromCookies(request);
        return ResponseEntity.ok(userDetailsService.createBankAccount(jwtUtils.getUserNameFromJwtToken(jwt)));

//        userDetailsService.

//        return ResponseEntity.ok(bankService.save(bankAccount));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/account/actions/deposit")
    public ResponseEntity<?> depositBankAccount(@Valid @RequestBody BankAccountRequest bankRequest) {

        return ResponseEntity.ok(bankService.deposit(bankRequest));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/account/actions/withdraw")
    public ResponseEntity<?> withdrawBankAccount(@Valid @RequestBody BankAccountRequest bankRequest) {

        return ResponseEntity.ok(bankService.withdraw(bankRequest));
    }

    //    @PreAuthorize("isAuthenticated()")
//    @PostMapping("/accounts/transfer/external")
//    public ResponseEntity transfer(@Valid @RequestBody BankAccountTransferRequest request) {
//        return ResponseEntity.ok(bankService.transfer(request));
//    }

        @PreAuthorize("isAuthenticated()")
    @PostMapping("/transfer")
    public ResponseEntity transfer(@RequestBody BankAccountTransferRequest request, HttpServletRequest req) {

            String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromCookies(req));

            if(!userDetailsService.existsBankAccount(username, request.getFromAccountNumber())){
                ResponseEntity.internalServerError().body(new HashMap<>().put("message", "You do not own fromAccount "));
            };

        return ResponseEntity.ok(bankService.transfer(request));
    }

//    @PreAuthorize("isAuthenticated()")
    @PostMapping("/transfer/external")
    public ResponseEntity transferExternal(@RequestBody BankAccountTransferRequest request) {
        return bankService.transferExternal(request);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/account")
    public ResponseEntity<?> deleteUser(HttpServletRequest request) {

        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromCookies(request));

        return userDetailsService.deleteByUsername(username);
        }
}
