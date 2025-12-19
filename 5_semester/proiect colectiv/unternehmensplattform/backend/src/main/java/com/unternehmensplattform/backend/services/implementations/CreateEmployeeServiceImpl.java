package com.unternehmensplattform.backend.services.implementations;

import com.unternehmensplattform.backend.entities.User;
import com.unternehmensplattform.backend.enums.UserRole;
import com.unternehmensplattform.backend.repositories.UserRepository;
import com.unternehmensplattform.backend.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateEmployeeServiceImpl {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Creates a new employee with the specified details and a default role of Employee.
     *
     * @param firstName the employee's first name
     * @param lastName  the employee's last name
     * @param email     the employee's unique email
     * @param password  the employee's password (to be encoded)
     * @param telefonNumber the employee's phone number
     * @return the created User entity with role Employee
     * @throws IllegalArgumentException if an employee with the given email already exists
     */
    public User createEmployee(String firstName, String lastName, String email, String password, String telefonNumber) {
        // Check if the email is already in use
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("An employee with this email already exists.");
        }

        // Create the new employee with Employee role
        User newEmployee = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .passwordHash(passwordEncoder.encode(password))  // Encode the password
                .accountLocked(false)                            // Set account as unlocked
                .enabled(true)                                   // Enable the account
                .telefonNumber(telefonNumber)
                .role(UserRole.Employee)                         // Set role as Employee
                .build();

        // Save and return the new employee
        return userRepository.save(newEmployee);
    }
}
