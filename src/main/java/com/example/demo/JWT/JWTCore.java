package com.example.demo.JWT;

import com.example.demo.config.properties.JWTProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class JWTCore {
//    private final JWTProperties jwtProperties;
//
//    public String generateToken(Authentication authentication) {
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        Date issuedDate = new Date(System.currentTimeMillis());
//        Date expirationDate = new Date(issuedDate.getTime() + jwtProperties.lifetime().toMillis());
//        return Jwts.builder()
//                .subject(userDetails.getUsername())
//                .issuedAt(issuedDate)
//                .expiration(expirationDate)
//                .signWith(Keys.hmacShaKeyFor(jwtProperties.secret().getBytes(StandardCharsets.UTF_8)))
//                .compact();
//    }
//
//    public String getUsernameFromToken(String token) {
//        return Jwts.parser()
//                .verifyWith(Keys.hmacShaKeyFor(jwtProperties.secret().getBytes(StandardCharsets.UTF_8)))
//                .build()
//                .parseSignedClaims(token)
//                .getPayload()
//                .getSubject();
//    }
//
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(jwtProperties.secret().getBytes(StandardCharsets.UTF_8))).build().parse(token);
//            return true;
//        } catch (BadCredentialsException e) {
//            log.warn("Ошибка при валидации токена: {}", e.getMessage());
//        }
//        return false;
//    }
//
//}
