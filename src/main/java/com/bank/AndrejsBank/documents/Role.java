package com.bank.AndrejsBank.documents;

import com.bank.AndrejsBank.model.ERole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//INSERT INTO roles(name) VALUES('ROLE_USER');
//INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
//INSERT INTO roles(name) VALUES('ROLE_ADMIN');

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="roles", schema = "public")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    public Role(ERole name) {
        this.name = name;
    }
}
