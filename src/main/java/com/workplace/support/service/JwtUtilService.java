package com.workplace.support.service;

import com.workplace.support.entity.Employee;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtUtilService {

    @Value("${jwt.secret.key}")
    private String secretKey ;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(Employee emp){

        Date now = new Date();

        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(emp.getEmpId().toString())
                .signWith(getSecretKey())
                .claim("role",emp.getRole().name())
                .issuedAt(now)
                .expiration(expiryDate)
                .compact();
    }


    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String extractEmployeeId(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
