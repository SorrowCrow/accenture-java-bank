package com.bank.AndrejsBank.interfaces;

import com.bank.AndrejsBank.documents.BankAccount;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface BankAccountRepository extends MongoRepository<BankAccount, String> {
    Optional<BankAccount> findByName(String name);
}
