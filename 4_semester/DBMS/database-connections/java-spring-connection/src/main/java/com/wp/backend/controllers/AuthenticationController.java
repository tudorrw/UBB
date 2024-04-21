package com.wp.backend.controllers;

import com.wp.backend.config.UserAuthenticationProvider;
import com.wp.backend.dtos.CredentialsDto;
import com.wp.backend.dtos.SignUpDto;
import com.wp.backend.dtos.UserDto;
import com.wp.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class AuthenticationController {
    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto) {
        UserDto user = userService.login(credentialsDto);
        user.setToken(userAuthenticationProvider.createToken(user.getLogin()));
        return ResponseEntity.ok(user);
    }
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto signUpDto) {
        UserDto user = userService.register(signUpDto);
        user.setToken(userAuthenticationProvider.createToken(user.getLogin()));
        return  ResponseEntity.created(URI.create("/users/" + user.getId()))
                .body(user);
    }
}
