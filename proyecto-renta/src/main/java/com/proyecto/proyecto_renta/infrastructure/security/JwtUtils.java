package com.proyecto.proyecto_renta.infrastructure.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Handles JWT token operations including generation, validation, and claims extraction
 */
@Component
public class JwtUtils {

    private final SecretKey signingKey;
    private final long tokenLifetime;

    public JwtUtils(
            @Value("${jwt.secret}") String encodedKey,
            @Value("${jwt.expiration}") long validityPeriod) {
        
        validateSecret(encodedKey);
        this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(encodedKey));
        this.tokenLifetime = validityPeriod;
    }

    private void validateSecret(String base64Key) {
        if (base64Key == null || base64Key.trim().isEmpty()) {
            throw new SecurityConfigurationException("JWT secret cannot be empty");
        }
        
        byte[] keyBytes = Decoders.BASE64.decode(base64Key);
        if (keyBytes.length < 32) {
            throw new SecurityConfigurationException("JWT secret must be at least 256 bits (32 bytes)");
        }
    }

    public String createToken(UserDetails user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tokenLifetime))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createToken(Map<String, Object> additionalClaims, UserDetails user) {
        Map<String, Object> claims = new ConcurrentHashMap<>(additionalClaims);
        claims.put("sub", user.getUsername());
        
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tokenLifetime))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isValidToken(String token, UserDetails user) {
        try {
            String username = getSubject(token);
            return username.equals(user.getUsername()) && !isExpired(token);
        } catch (JwtException e) {
            return false;
        }
    }

    public String getSubject(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public <T> T getClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(parseTokenClaims(token));
    }

    private boolean isExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private Claims parseTokenClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private static class SecurityConfigurationException extends RuntimeException {
        public SecurityConfigurationException(String message) {
            super(message);
        }
    }
}

