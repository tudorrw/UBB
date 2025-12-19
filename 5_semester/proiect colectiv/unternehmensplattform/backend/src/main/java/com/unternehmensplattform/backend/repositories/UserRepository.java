package com.unternehmensplattform.backend.repositories;

import com.unternehmensplattform.backend.entities.Company;
import com.unternehmensplattform.backend.entities.User;
import com.unternehmensplattform.backend.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String username);

    boolean existsByTelefonNumber(String telefonNumber);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.contract c WHERE u.role = :role AND c.company = :company")
    List<User> findUsersByCompany(@Param("role") UserRole role, @Param("company") Company company);

    List<User> findByRole(UserRole userRole);

    @Query("SELECT u FROM User u JOIN u.contract c WHERE u.role = :role AND c.company = :company")
    List<User> findUsersByRoleAndCompany(@Param("role") UserRole role, @Param("company") Company company);



}
