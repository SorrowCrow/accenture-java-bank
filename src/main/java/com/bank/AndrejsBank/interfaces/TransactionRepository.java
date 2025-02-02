package com.bank.AndrejsBank.interfaces;

import com.bank.AndrejsBank.documents.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

//    List<Transaction> findByFrombankaccountId(String id);
//
//    @Transactional
//    void deleteByFromBankaccountId(String id);
//
//    List<Transaction> findByTobankaccountId(String id);
//
//    @Transactional
//    void deleteByToBankaccountId(String id);
}