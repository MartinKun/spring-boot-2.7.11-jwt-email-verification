package com.example.confirmemailregistration.service;

import com.example.confirmemailregistration.security.controller.request.Email;

public interface EmailService {

    void sendEmail(Email email);

}
