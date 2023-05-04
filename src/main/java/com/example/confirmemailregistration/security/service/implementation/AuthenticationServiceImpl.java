package com.example.confirmemailregistration.security.service.implementation;

import com.example.confirmemailregistration.controller.request.Email;
import com.example.confirmemailregistration.security.controller.request.AuthenticationRequest;
import com.example.confirmemailregistration.security.controller.request.RegisterRequest;
import com.example.confirmemailregistration.security.controller.response.AuthenticationResponse;
import com.example.confirmemailregistration.security.jwt.JwtProvider;
import com.example.confirmemailregistration.security.model.entity.Role;
import com.example.confirmemailregistration.security.model.entity.User;
import com.example.confirmemailregistration.security.model.enums.RoleEnum;
import com.example.confirmemailregistration.security.repository.RoleRepository;
import com.example.confirmemailregistration.security.repository.UserRepository;
import com.example.confirmemailregistration.security.service.AuthenticationService;
import com.example.confirmemailregistration.service.EmailService;
import com.example.confirmemailregistration.utils.EmailTemplates;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final EmailService emailService;

    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail()))
            throw new RuntimeException("email is already in use");

        String password = passwordEncoder.encode(request.getPassword());
        Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("role does not exist"));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        User user = User.builder()
                .email(request.getEmail())
                .password(password)
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .roles(roles)
                .looked(true)
                .build();

        userRepository.save(user);

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );
        String token = jwtProvider.generateConfirmationToken(authentication);

        Email email = Email.builder()
                .recipient(user.getEmail())
                .subject("Confirmación de registro en nuestro sitio web")
                .body(EmailTemplates.getConfirmationEmailTemplate(token))
                .build();
        emailService.sendEmail(email);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateAccessToken(authentication);

        User user = (User) authentication.getPrincipal();
        if (user.isLooked())
            throw new RuntimeException("The user has not confirmed their account.");

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public void confirmUser(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid token");
        }

        String confirmToken = authHeader.substring(7);

        User user = userRepository.findByEmail(jwtProvider.extractUsernameFromConfirmationToken(confirmToken))
                .orElseThrow(() -> new RuntimeException("User does not exist"));

        user.setLooked(false);
        userRepository.save(user);
    }

}