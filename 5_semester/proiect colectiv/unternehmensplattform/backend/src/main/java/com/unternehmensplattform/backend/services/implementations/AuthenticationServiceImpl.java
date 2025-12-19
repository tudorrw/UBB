package com.unternehmensplattform.backend.services.implementations;

import com.unternehmensplattform.backend.entities.Company;
import com.unternehmensplattform.backend.entities.Contract;
import com.unternehmensplattform.backend.entities.DTOs.AuthenticationRequest;
import com.unternehmensplattform.backend.entities.DTOs.AuthenticationResponse;
import com.unternehmensplattform.backend.entities.DTOs.RegistrationRequest;
import com.unternehmensplattform.backend.entities.User;
import com.unternehmensplattform.backend.enums.UserRole;
import com.unternehmensplattform.backend.handler.DuplicateEmailException;
import com.unternehmensplattform.backend.handler.DuplicatePhoneNumberException;
import com.unternehmensplattform.backend.repositories.CompanyRepository;
import com.unternehmensplattform.backend.repositories.ContractRepository;
import com.unternehmensplattform.backend.repositories.UserRepository;
import com.unternehmensplattform.backend.security.JwtService;
import com.unternehmensplattform.backend.services.interfaces.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ContractRepository contractRepository;
    private final CompanyRepository companyRepository;

    private void duplicatedFields(RegistrationRequest registrationRequest) {
        if (registrationRequest.getEmail() != null && !registrationRequest.getEmail().isEmpty() &&
                userRepository.existsByEmail(registrationRequest.getEmail())) {
            throw new DuplicateEmailException("Email already in use.");
        }

        if (registrationRequest.getTelefonNumber() != null && !registrationRequest.getTelefonNumber().isEmpty() &&
                userRepository.existsByTelefonNumber(registrationRequest.getTelefonNumber())) {
            throw new DuplicatePhoneNumberException("Phone number already in use.");
        }
    }

    public void register(RegistrationRequest registrationRequest) {
        var currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var contract = currentUser.getContract();
        if((currentUser.getRole() == UserRole.Administrator && contract != null) || currentUser.getRole() == UserRole.Superadmin) {

            duplicatedFields(registrationRequest);

            UserRole roleToAssign = getRole(currentUser);
            var createdUser = User.builder()
                    .firstName(registrationRequest.getFirstName())
                    .lastName(registrationRequest.getLastName())
                    .email(registrationRequest.getEmail())
                    .passwordHash(passwordEncoder.encode(registrationRequest.getPasswordHash()))
                    .accountLocked(false)
                    .telefonNumber(registrationRequest.getTelefonNumber())
                    .enabled(true)
                    .role(roleToAssign)
                    .build();
            userRepository.save(createdUser);

            if(currentUser.getRole() == UserRole.Administrator && contract != null) {
                Company company = contract.getCompany();
                Contract contractToAssign = new Contract();

                contractToAssign.setCompany(company);
                contractToAssign.setUser(createdUser);
                contractToAssign.setSigningDate(registrationRequest.getSigningDate());


                LocalDate signingDate = contractToAssign.getSigningDate();
                LocalDate currentDate = LocalDate.now();

                int totalVacationDaysPerYear = 21;
                int actualYearVacationDays = totalVacationDaysPerYear;

                if (signingDate.getYear() == currentDate.getYear()) {
                    LocalDate lastDayOfActualYear = LocalDate.of(currentDate.getYear(), 12, 31);
                    long remainingDaysFromActualYear = ChronoUnit.DAYS.between(signingDate, lastDayOfActualYear.plusDays(1));
                    actualYearVacationDays = (int) (remainingDaysFromActualYear * totalVacationDaysPerYear / 365.0);
                }

                contractToAssign.setActualYearVacationDays(actualYearVacationDays);

                contractRepository.save(contractToAssign);
            }
        }
    }
    public void register(RegistrationRequest registrationRequest, Integer companyId) {
        var currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(currentUser.getRole() == UserRole.Superadmin) {

            duplicatedFields(registrationRequest);

            UserRole roleToAssign = getRole(currentUser);
            var createdUser = User.builder()
                    .firstName(registrationRequest.getFirstName())
                    .lastName(registrationRequest.getLastName())
                    .email(registrationRequest.getEmail())
                    .passwordHash(passwordEncoder.encode(registrationRequest.getPasswordHash()))
                    .accountLocked(false)
                    .telefonNumber(registrationRequest.getTelefonNumber())
                    .enabled(true)
                    .role(roleToAssign)
                    .build();

            userRepository.save(createdUser);
            Company company = companyRepository.findById(companyId).orElse(null);
            if(company == null) {
                throw new IllegalArgumentException("Company not found.");
            }
            Contract contractToAssign = new Contract();

            contractToAssign.setCompany(company);
            contractToAssign.setUser(createdUser);
            contractToAssign.setSigningDate(registrationRequest.getSigningDate());
            contractRepository.save(contractToAssign);
        }
    }

    public void registerSuperadmins(RegistrationRequest registrationRequest) {
        duplicatedFields(registrationRequest);

        var user = User.builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .email(registrationRequest.getEmail())
                .passwordHash(passwordEncoder.encode(registrationRequest.getPasswordHash()))
                .accountLocked(false)
                .enabled(true)
                .role(UserRole.Superadmin)
                .telefonNumber(registrationRequest.getTelefonNumber())
                .build();
        userRepository.save(user);
    }

    private static UserRole getRole(User currentUser) {
        UserRole roleToAssign;
        // Check if the current user has permission to create the requested role
        if (currentUser.getRole() == UserRole.Superadmin) {
            roleToAssign = UserRole.Administrator; // Superadmin can create administrators
        } else if (currentUser.getRole() == UserRole.Administrator) {
            roleToAssign = UserRole.Employee; // Administrator can create employees
        } else {
            throw new IllegalArgumentException("You do not have permission to create users.");
        }
        return roleToAssign;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPasswordHash()
                )
        );
        var claims = new HashMap<String, Object>();
        var user = ((User)auth.getPrincipal());
        claims.put("full_name", user.getFirstName() + " " + user.getLastName());
        var jwtToken = jwtService.generateToken(claims, user);
        return AuthenticationResponse.builder()
                .token(jwtToken).build();
    }
}
