package com.library_rental_system.rental.util;

import com.library_rental_system.rental.model.User;
import com.library_rental_system.rental.service.CustomAuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expirationTime;

    public String createToken(User user) {
        Claims claims = Jwts.claims()
                .setSubject(user.getEmail());
        // token content
        claims.put("email", user.getEmail());
        claims.put("id", user.getId());
        Date tokenCreateTime = new Date(); // token creation time
        Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(expirationTime)); // token expiration time

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(tokenValidity)
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean validateToken(String token, CustomAuthService customAuthService) {
        String email = extractEmail(token);
        Date expirationDate = extractExpiration(token);
        return customAuthService.loadUserByUsername(email).equals(email) && !expirationDate.before(new Date());
    }


    // token expiration date
    // token email
    public String extractEmail(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    private Date extractExpiration(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration();
    }

    private Key getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
