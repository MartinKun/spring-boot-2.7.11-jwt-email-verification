package com.example.confirmemailregistration.security.service;

import com.example.confirmemailregistration.security.controller.request.AuthenticationRequest;
import com.example.confirmemailregistration.security.controller.request.RegisterRequest;
import com.example.confirmemailregistration.security.controller.response.AuthenticationResponse;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    void register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void confirmUser(HttpServletRequest request);

}
