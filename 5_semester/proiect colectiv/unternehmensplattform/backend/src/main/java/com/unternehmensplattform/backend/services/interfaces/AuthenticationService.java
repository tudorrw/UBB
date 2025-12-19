package com.unternehmensplattform.backend.services.interfaces;

import com.unternehmensplattform.backend.entities.DTOs.AuthenticationRequest;
import com.unternehmensplattform.backend.entities.DTOs.AuthenticationResponse;
import com.unternehmensplattform.backend.entities.DTOs.RegistrationRequest;

public interface AuthenticationService {
    void register(RegistrationRequest registrationRequest);
    void register(RegistrationRequest registrationRequest, Integer companyId);
    void registerSuperadmins(RegistrationRequest registrationRequest);


    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

}
