package com.unternehmensplattform.backend.controllers;

import com.unternehmensplattform.backend.services.interfaces.AuthenticationService;
import com.unternehmensplattform.backend.services.interfaces.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("contract")
@RequiredArgsConstructor
public class ContractController {
    private final AuthenticationService authenticationService;
    private final UserDetailsService userDetailsService;
    private final ContractService contractService;
}
