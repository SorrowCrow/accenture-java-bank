package com.bank.AndrejsBank.services;

import com.bank.AndrejsBank.documents.BankAccount;
import com.bank.AndrejsBank.documents.User;
//import com.bank.AndrejsBank.interfaces.TransactionRepository;
import com.bank.AndrejsBank.interfaces.TransactionRepository;
import com.bank.AndrejsBank.interfaces.UserRepository;
import com.bank.AndrejsBank.model.response.MessageResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    TransactionRepository transactionRepository;

    public List<UserDetailsImpl> findAll() {
        return userRepository.findAll().stream().map(el -> UserDetailsImpl.build(el)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

    @Transactional
    public boolean existsBankAccount(String username, String bankAccountId) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        BankAccount eq = new BankAccount();
        eq.setId(bankAccountId);

        return user.getBankaccounts().contains(eq);
    }

    public BankAccount createBankAccount(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        BankAccount acc = new BankAccount();
//
//        acc.setId(user.getSurname().toUpperCase().substring(0, 3) + user.getName().toUpperCase().substring(0, 3) + "_" + UUID.randomUUID().toString().replace("-", "").substring(0, 12));

        acc.setId("MATAND_" + UUID.randomUUID().toString().replace("-", "").substring(0, 12));

        BankAccount saved = bankAccountService.save(acc);

        user.addBankAccount(saved);
        userRepository.save(user);
        UserDetailsImpl.build(user);
        return saved;
    }

    @Transactional
    public ResponseEntity deleteByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        user.getBankaccounts().forEach(bankAccount -> {
//            bankAccount.getTransactions().forEach(transaction -> transactionRepository.delete(transaction));
//            bankAccount.removeTransactions();
            bankAccountService.deleteById(bankAccount.getId());
        });

        user.removeRoles();

        user.removeBankAccounts();

        userRepository.deleteByUsername(username);
        return ResponseEntity.ok(new MessageResponse("Success"));
    }
}