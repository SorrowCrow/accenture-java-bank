package com.bank.AndrejsBank.interfaces;

import com.bank.AndrejsBank.documents.BankAccount;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
//    void deleteByUsername (String username);
}
