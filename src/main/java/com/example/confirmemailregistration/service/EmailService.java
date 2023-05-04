package com.example.confirmemailregistration.service;

import com.example.confirmemailregistration.controller.request.Email;

public interface EmailService {

    void sendEmail(Email email);

}
