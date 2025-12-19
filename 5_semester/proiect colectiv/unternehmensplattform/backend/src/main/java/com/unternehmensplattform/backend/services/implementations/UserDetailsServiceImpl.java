package com.unternehmensplattform.backend.services.implementations;


import com.unternehmensplattform.backend.entities.Company;
import com.unternehmensplattform.backend.entities.Contract;
import com.unternehmensplattform.backend.entities.DTOs.CompanyDTO;
import com.unternehmensplattform.backend.entities.DTOs.UserDetailsDTO;
import com.unternehmensplattform.backend.entities.User;
import com.unternehmensplattform.backend.enums.UserRole;
import com.unternehmensplattform.backend.handler.DuplicateEmailException;
import com.unternehmensplattform.backend.handler.DuplicatePhoneNumberException;
import com.unternehmensplattform.backend.repositories.CompanyRepository;
import com.unternehmensplattform.backend.repositories.UserRepository;
import com.unternehmensplattform.backend.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


    @Override
    public List<UserDetailsDTO> getAllEmployees() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        if (currentUser.getRole() == UserRole.Administrator) {
            Contract contract = currentUser.getContract();
            Company company = contract.getCompany();
            List<User> employees = userRepository.findUsersByCompany(UserRole.Employee, company);

            return employees.stream()
                    .map(this::convertToUserDetailsDTO)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("User is not an administrator");
        }
    }

    @Override
    @Transactional
    public void modifyUser(UserDetailsDTO userDetailsDTO) {
        User user = userRepository.findById(userDetailsDTO.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (userDetailsDTO.getEmail() != null && !userDetailsDTO.getEmail().isEmpty() &&
                !user.getEmail().equals(userDetailsDTO.getEmail()) &&
                userRepository.existsByEmail(userDetailsDTO.getEmail())) {
            throw new DuplicateEmailException("Email already in use.");
        }

        if (userDetailsDTO.getTelefonNumber() != null && !userDetailsDTO.getTelefonNumber().isEmpty() &&
                !user.getTelefonNumber().equals(userDetailsDTO.getTelefonNumber()) &&
                userRepository.existsByTelefonNumber(userDetailsDTO.getTelefonNumber())) {
            throw new DuplicatePhoneNumberException("Phone number already in use.");
        }

        user.setFirstName(userDetailsDTO.getFirstName());
        user.setLastName(userDetailsDTO.getLastName());
        user.setEmail(userDetailsDTO.getEmail());
        user.setTelefonNumber(userDetailsDTO.getTelefonNumber());
        user.setAccountLocked(userDetailsDTO.isAccountLocked());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deactivateUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(user.isAccountNonLocked()) {
            user.setEnabled(false);
            user.setAccountLocked(true);
        }
        userRepository.save(user);

    }

    @Override
    @Transactional
    public void activateUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(user.isAccountLocked()) {
            user.setEnabled(true);
            user.setAccountLocked(false);
        }
        userRepository.save(user);
    }

    public List<UserDetailsDTO> getAllAdmins(CompanyDTO companyDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();


        if (currentUser.getRole() == UserRole.Superadmin) {
            Company company = companyRepository.findById(companyDTO.getCompanyId()).orElseThrow(() -> new RuntimeException("Company not found"));
            List<User> employees = userRepository.findUsersByCompany(UserRole.Administrator, company);

            return employees.stream()
                    .map(this::convertToUserDetailsDTO)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("User is not an superadmin");
        }
    }


    @Override
    @Transactional(readOnly = true)
    public boolean emailExists(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException("Email already in use.");
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean phoneNumberExists(String phoneNumber) {
        if (userRepository.existsByTelefonNumber(phoneNumber)) {
            throw new DuplicatePhoneNumberException("Phone number already in use.");
        }
        return false;
    }

    public UserDetailsDTO convertToUserDetailsDTO(User user) {
        UserDetailsDTO.UserDetailsDTOBuilder builder = UserDetailsDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .telefonNumber(user.getTelefonNumber())
                .accountLocked(user.isAccountLocked())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .role(user.getRole());

        // Include company name if the user has a contract with a company
        Contract contract = user.getContract();
        if (contract != null) {
            builder.companyName(contract.getCompany().getName())
                    .signingDate(contract.getSigningDate())
                    .actualYearVacationDays(contract.getActualYearVacationDays());
        }

        return builder.build();
    }
}
