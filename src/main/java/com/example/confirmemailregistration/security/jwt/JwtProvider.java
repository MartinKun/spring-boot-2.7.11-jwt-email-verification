package com.example.confirmemailregistration.security.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.access-token.secret}")
    private String accessTokenSecret;

    @Value("${jwt.confirm-token.secret}")
    private String confirmationTokenSecret;

    @Value("${jwt.access-token.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.confirm-token.expiration}")
    private long confirmationTokenExpiration;

    public String generateConfirmationToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + confirmationTokenExpiration))
                .signWith(getKey(confirmationTokenSecret))
                .compact();
    }

    public String generateAccessToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(getKey(accessTokenSecret))
                .compact();
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getKey(accessTokenSecret))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("Invalid JWT access token: {}", e.getMessage());
            return false;
        }
    }

    public String extractUsernameFromAccessToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey(accessTokenSecret))
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public String extractUsernameFromConfirmationToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey(confirmationTokenSecret))
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    private List<String> getRoles(UserDetails userDetails) {
        return userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    private Key getKey(String secret) {
        byte[] secretBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(secretBytes);
    }

}