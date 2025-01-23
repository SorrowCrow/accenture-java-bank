package com.bank.AndrejsBank.documents;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class BankAccount {
    @Id
    private String id;

    private double balance;

    @Indexed(unique = true)
    private String name;

    public void deposit(double amount){
        this.balance += amount;
    }

    public void withdraw(double amount){
        this.balance -= amount;
    }

    public void printBalance(){
        System.out.println(balance);
    }

    public void transfer(BankAccount b){
        b.deposit(balance);
        this.balance=0;
    }

}