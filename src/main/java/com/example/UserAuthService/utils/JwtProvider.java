package com.example.UserAuthService.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    private String secretKey;
    private long validityInMilliseconds;

    @Autowired
    public JwtProvider(@Value("${security.jwt.token.secret-key}") String secretKey,
                       @Value("${security.jwt.token.expiration}")long validityInMilliseconds) {

        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.validityInMilliseconds = validityInMilliseconds;
    }

    /**
     * Create JWT string given username and roles.
     *
     * @param username
     * @return jwt string
     */
    public String createToken(String username) {
        Claims claims = Jwts.claims().setSubject(username);
       /* claims.put(ROLES_KEY, roles.stream().map(role ->new SimpleGrantedAuthority(role.getAuthority()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));*/
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiresAt)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * Validate the JWT String
     *
     * @param token JWT string
     * @return true if valid, false otherwise
     */
    public boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Get the username from the token string
     *
     * @param token jwt
     * @return username
     */
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody().getSubject();
    }
}
