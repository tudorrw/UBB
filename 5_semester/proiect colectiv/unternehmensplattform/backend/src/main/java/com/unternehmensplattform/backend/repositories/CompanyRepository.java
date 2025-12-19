package com.unternehmensplattform.backend.repositories;

import com.unternehmensplattform.backend.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    boolean existsByName(String name);
    boolean existsByTelefonNumber(String telefonNumber);
}
