package com.unternehmensplattform.backend.services.interfaces;

import com.unternehmensplattform.backend.entities.DTOs.CompanyDTO;
import com.unternehmensplattform.backend.entities.DTOs.UserDetailsDTO;

import java.util.List;

public interface UserService {
    List<UserDetailsDTO> getAllEmployees();

    void modifyUser( UserDetailsDTO userDetailsDTO);
    void deactivateUser(Integer userId);
    void activateUser(Integer userId);
    List<UserDetailsDTO> getAllAdmins(CompanyDTO companyDTO);

    boolean phoneNumberExists(String phoneNumber);
    boolean emailExists(String email);


    }
