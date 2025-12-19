package com.unternehmensplattform.backend.entities.DTOs;

import com.unternehmensplattform.backend.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailsDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String fullName;
    @Email(message = "Email is not formatted")
    @NotEmpty(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    private String email;
    private String telefonNumber;
    private boolean accountLocked;
    private UserRole role;

    // Additional fields for company and contract information
    private String companyName; // To hold the company name
    private LocalDate signingDate; // Contract signing date
    private Integer actualYearVacationDays; // Actual year vacation days
}
