package com.unternehmensplattform.backend.repositories;

import com.unternehmensplattform.backend.entities.Contract;
import com.unternehmensplattform.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Integer> {
    Optional<Contract> findByUser(User employee);
}
