package com.rahul.cartify.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET =
            "mysecretkeymysecretkeymysecretkey12";

    // generate secret key
    private SecretKey getSignInKey() {

        return Keys.hmacShaKeyFor(
                SECRET.getBytes()
        );
    }

    // generate token
    public String generateToken(String email) {

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000 * 60 * 60
                        )
                )
                .signWith(getSignInKey())
                .compact();
    }

    // extract username
    public String extractUsername(String token) {

        return extractClaims(token)
                .getSubject();
    }

    // extract claims
    private Claims extractClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}