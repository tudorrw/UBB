package com.unternehmensplattform.backend.services.implementations;

import com.unternehmensplattform.backend.entities.Company;
import com.unternehmensplattform.backend.entities.Contract;
import com.unternehmensplattform.backend.entities.DTOs.*;
import com.unternehmensplattform.backend.entities.User;
import com.unternehmensplattform.backend.enums.UserRole;
import com.unternehmensplattform.backend.handler.DuplicateEmailException;
import com.unternehmensplattform.backend.handler.DuplicateNameException;
import com.unternehmensplattform.backend.handler.DuplicatePhoneNumberException;
import com.unternehmensplattform.backend.handler.NoAdminProvidedException;
import com.unternehmensplattform.backend.repositories.CompanyRepository;
import com.unternehmensplattform.backend.repositories.ContractRepository;
import com.unternehmensplattform.backend.repositories.UserRepository;
import com.unternehmensplattform.backend.services.interfaces.CompanyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final AuthenticationServiceImpl authenticationService;
    private final ContractRepository contractRepository;

    @Override
    public List<CompanyDetailsDTO> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();

        return companies.stream().map(company -> CompanyDetailsDTO.builder()
                        .companyDTO(CompanyDTO.builder()
                                .companyId(company.getId())
                                .name(company.getName())
                                .telefonNumber(company.getTelefonNumber())
                                .address(company.getAddress())
                                .build())
                        .admins(userRepository.findUsersByCompany(UserRole.Administrator, company).stream()
                                .map(admin -> UserDetailsDTO.builder()
                                        .id(admin.getId())
                                        .firstName(admin.getFirstName())
                                        .lastName(admin.getLastName())
                                        .email(admin.getEmail())
                                        .telefonNumber(admin.getTelefonNumber())
                                        .accountLocked(admin.isAccountLocked())
                                        .role(admin.getRole())
                                        .companyName(company.getName())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }


    private void duplicatedFieldsCompany(CompanyDTO companyDTO) {
        if (companyDTO.getName() != null && !companyDTO.getName().isEmpty() &&
                companyRepository.existsByName(companyDTO.getName())) {
            throw new DuplicateNameException("Company name already in use.");
        }

        if (companyDTO.getTelefonNumber() != null && !companyDTO.getTelefonNumber().isEmpty() &&
                companyRepository.existsByTelefonNumber(companyDTO.getTelefonNumber())) {
            throw new DuplicatePhoneNumberException("Phone number already in use.");
        }
    }

    public void duplicatedFieldsAdmin(RegistrationRequest registrationRequest) {
        if (registrationRequest.getEmail() != null && !registrationRequest.getEmail().isEmpty() &&
                userRepository.existsByEmail(registrationRequest.getEmail())) {
            throw new DuplicateEmailException("Email already in use.");
        }

        if (registrationRequest.getTelefonNumber() != null && !registrationRequest.getTelefonNumber().isEmpty() &&
                userRepository.existsByTelefonNumber(registrationRequest.getTelefonNumber())) {
            throw new DuplicatePhoneNumberException("Phone number already in use.");
        }
    }


    @Override
    @Transactional
    public void createCompanyWithAdmins(CompanyWithAdminsDTO companyWithAdminsDTO) {
        duplicatedFieldsCompany(companyWithAdminsDTO.getCompanyDTO());

        Company company = Company.builder()
                .name(companyWithAdminsDTO.getCompanyDTO().getName())
                .telefonNumber(companyWithAdminsDTO.getCompanyDTO().getTelefonNumber())
                .address(companyWithAdminsDTO.getCompanyDTO().getAddress())
                .build();

        boolean savedCompany = false;

        if (companyWithAdminsDTO.getAdminRegistrations() != null && !companyWithAdminsDTO.getAdminRegistrations().isEmpty()) {
            for (RegistrationRequest adminRegistration : companyWithAdminsDTO.getAdminRegistrations()) {
                duplicatedFieldsAdmin(adminRegistration);

                authenticationService.register(adminRegistration);

                User createdAdmin = userRepository.findByEmail(adminRegistration.getEmail())
                        .orElseThrow(() -> new RuntimeException("Admin user creation failed"));

                if (createdAdmin != null && !savedCompany) {
                    companyRepository.save(company);
                    savedCompany = true;
                }
                assignAdminToCompany(createdAdmin, company);
            }
        } else {
            throw new NoAdminProvidedException("At least one admin registration must be provided.");
        }
    }


    @Transactional
    public void assignAdminToCompany(Integer companyId, Integer adminId) {
        // Retrieve company by ID
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        // Retrieve admin by ID
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        if(admin.getRole() != UserRole.Administrator ){
            throw new IllegalArgumentException("The User is not an admin.");

        }
        // Create and save contract to assign admin to company
        Contract contract = new Contract();
        contract.setCompany(company);
        contract.setUser(admin);

        contractRepository.save(contract);
    }

    private void assignAdminToCompany(User createdAdmin, Company company) {
        Contract contract = Contract.builder()
                .company(company)
                .user(createdAdmin)
                .build();

        contractRepository.save(contract);
    }

    private CompanyDTO convertToDTO(Company company) {
        return CompanyDTO.builder()
                .companyId(company.getId())
                .name(company.getName())
                .telefonNumber(company.getTelefonNumber())
                .address(company.getAddress())
                .build();
    }
}
