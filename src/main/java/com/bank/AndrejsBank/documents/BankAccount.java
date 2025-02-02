package com.bank.AndrejsBank.documents;

import com.bank.AndrejsBank.interfaces.BankAccountIdGenerator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "public")
public class BankAccount implements Serializable {
    //save into file csv
    @Id
    @Column
    private String id;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "bankaccount_transactions",
            joinColumns = @JoinColumn(name = "bankaccount_id"),
            inverseJoinColumns = @JoinColumn(name = "transaction_id"))
    public Set<Transaction> transactions;

    public void removeTransactions(){
        this.transactions.clear();
    }

    //    @NotNull(message = "First balance is required")
    @Column
    private BigDecimal balance= new BigDecimal("0.00");

    public void deposit(Transaction transaction){
        setBalance(balance.add(transaction.getAmount()));
        transactions.add(transaction);
    }

//    public void deposit(String fromId, BigDecimal amount){
//        setBalance(balance.add(amount));
//        addTransaction(amount, fromId);
//    }

    public void withdraw(Transaction transaction){
        setBalance(balance.subtract(transaction.getAmount()));
        transactions.add(transaction);
    }

//    public void withdraw(String fromId, BigDecimal amount){
//        setBalance(balance.subtract(amount));
//        addTransaction(amount.negate(), fromId);
//    }

    @Override
    public boolean equals (Object object) {
        if (object == null || object.getClass() != getClass()) {
            return false;
        }
        BankAccount bankAccount = (BankAccount) object;
        return Objects.equals(this.id, bankAccount.getId());
    }

}