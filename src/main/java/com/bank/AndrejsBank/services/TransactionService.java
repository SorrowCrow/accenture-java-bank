package com.bank.AndrejsBank.services;

import com.bank.AndrejsBank.documents.BankAccount;
import com.bank.AndrejsBank.documents.Transaction;
import com.bank.AndrejsBank.interfaces.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public void addTransaction(BankAccount toBankAccount, BigDecimal amount) {
        Transaction tr = new Transaction();
        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            tr.setToId(toBankAccount.getId());
            tr.setFromId("");
            tr.setAmount(amount);

            toBankAccount.deposit(tr);
        } else {
            tr.setToId("");
            tr.setFromId(toBankAccount.getId());
            tr.setAmount(amount.negate());

            toBankAccount.withdraw(tr);
        }
        tr.setAmount(amount);
        transactionRepository.save(tr);
    }

    public void addTransaction(BankAccount toBankAccount, BigDecimal amount, String fromBankAccount) {
        Transaction tr = new Transaction();
        tr.setToId(toBankAccount.getId());
        tr.setFromId(fromBankAccount);

        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            tr.setAmount(amount);
            //            fromBankAccount.withdraw(tr);
            toBankAccount.deposit(tr);
        } else {
            tr.setAmount(amount.negate());
//            fromBankAccount.deposit(tr);
            toBankAccount.withdraw(tr);
        }

        tr.setAmount(amount);
        transactionRepository.save(tr);
    }


}
