package com.bank.AndrejsBank.documents;

import com.bank.AndrejsBank.interfaces.TransactionRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private BigDecimal amount;

//    @Column
    private String fromId;

//    @Column
    private String toId;

    @Override
    public boolean equals (Object object) {
        if (object == null || object.getClass() != getClass()) {
            return false;
        }
        Transaction transaction = (Transaction) object;
        return Objects.equals(this.id, transaction.getId());
    }

}