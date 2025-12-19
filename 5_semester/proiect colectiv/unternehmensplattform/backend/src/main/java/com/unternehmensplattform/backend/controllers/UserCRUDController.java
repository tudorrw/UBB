package com.unternehmensplattform.backend.controllers;

import com.unternehmensplattform.backend.entities.Contract;
import com.unternehmensplattform.backend.entities.DTOs.*;
import com.unternehmensplattform.backend.entities.User;
import com.unternehmensplattform.backend.enums.UserRole;
import com.unternehmensplattform.backend.services.interfaces.AuthenticationService;
import com.unternehmensplattform.backend.services.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserCRUDController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @GetMapping("/get-employees")
    public ResponseEntity<List<UserDetailsDTO>> getAllEmployees() {
        List<UserDetailsDTO> employees = userService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/get-admins")
    public ResponseEntity<List<UserDetailsDTO>> getAllAdmins(
            @RequestBody @Valid CompanyDTO companyDTO
    ) {
        List<UserDetailsDTO> admins = userService.getAllAdmins(companyDTO);
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDetailsDTO> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        UserDetailsDTO.UserDetailsDTOBuilder userDetailsDTOBuilder = UserDetailsDTO.builder()
                .id(currentUser.getId())
                .firstName(currentUser.getFirstName())
                .lastName(currentUser.getLastName())
                .email(currentUser.getEmail())
                .telefonNumber(currentUser.getTelefonNumber())
                .accountLocked(currentUser.isAccountLocked())
                .role(currentUser.getRole());

        Contract contract = currentUser.getContract();
        if (contract != null) {
            userDetailsDTOBuilder
                    .companyName(contract.getCompany().getName())
                    .signingDate(contract.getSigningDate())
                    .actualYearVacationDays(contract.getActualYearVacationDays());
        }

        UserDetailsDTO userDetailsDTO = userDetailsDTOBuilder.build();
        return ResponseEntity.ok(userDetailsDTO);
    }


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(
            @RequestBody @Valid RegistrationRequest request) {
        authenticationService.register(request);
        return ResponseEntity.accepted().build();
    }
    @PostMapping("/register/{companyId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(
            @RequestBody @Valid RegistrationRequest request,
            @PathVariable @Valid Integer companyId) {
        authenticationService.register(request, companyId);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/modify")
    public ResponseEntity<?> modifyUser(
         @RequestBody @Valid UserDetailsDTO userDetailsDTO) {
        userService.modifyUser(userDetailsDTO);
        return ResponseEntity.accepted().build();
    }


    @PostMapping("/deactivate/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> deactivateUser(@PathVariable Integer userId) {
        userService.deactivateUser(userId);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/activate/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> activateUser(@PathVariable Integer userId) {
        userService.activateUser(userId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/check-email/{email}")
    public ResponseEntity<Boolean> checkEmailExists(@PathVariable String email) {
        boolean exists = userService.emailExists(email);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/check-phone/{phoneNumber}")
    public ResponseEntity<Boolean> checkPhoneNumberExists(@PathVariable String phoneNumber) {
        boolean exists = userService.phoneNumberExists(phoneNumber);
        return ResponseEntity.ok(exists);
    }

}
