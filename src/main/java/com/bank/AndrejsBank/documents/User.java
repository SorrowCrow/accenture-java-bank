package com.bank.AndrejsBank.documents;


import com.bank.AndrejsBank.interfaces.BankAccountIdGenerator;
import com.bank.AndrejsBank.util.Generator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.IdGeneratorType;

import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")
}, schema = "public", name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int id;

    @NotBlank
    @Size(max = 20)
    @Column
    private String username;

    @NotBlank
    @Size(max = 20)
    @Column
    private String name;

    @NotBlank
    @Size(max = 20)
    @Column
    private String surname;

    @NotBlank
    @Size(max = 120)
    @Column
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_bankaccounts",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "bankaccount_id"))
    public Set<BankAccount> bankaccounts;

    public void removeRoles(){
        this.roles.clear();
    }

    public void removeBankAccounts(){
        this.bankaccounts.clear();
    }

    public void addBankAccount(BankAccount bnk){ this.bankaccounts.add(bnk);}

    public User(String username,String name, String surname, String password) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
    }

}
