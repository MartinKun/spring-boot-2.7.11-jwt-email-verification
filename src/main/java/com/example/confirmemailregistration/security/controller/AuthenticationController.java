package com.example.confirmemailregistration.security.controller;

import com.example.confirmemailregistration.security.controller.request.AuthenticationRequest;
import com.example.confirmemailregistration.security.controller.request.RegisterRequest;
import com.example.confirmemailregistration.security.controller.response.AuthenticationResponse;
import com.example.confirmemailregistration.security.service.implementation.AuthenticationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationServiceImpl service;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request
    ) {
        service.register(request);
        return ResponseEntity.ok("Email sent.");
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        AuthenticationResponse response = service.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/confirm-user")
    public ResponseEntity<String> confirmUser(
            HttpServletRequest request
    ) {
        service.confirmUser(request);
        return ResponseEntity.ok("User was confirmed!");
    }
}
