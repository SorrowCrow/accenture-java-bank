package com.bank.AndrejsBank.interfaces;

import com.bank.AndrejsBank.documents.Role;
import com.bank.AndrejsBank.model.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
