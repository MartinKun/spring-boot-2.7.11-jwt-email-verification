package com.example.confirmemailregistration.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailSenderSettings {
    private String username;
    private String password;
    private String host;
}
