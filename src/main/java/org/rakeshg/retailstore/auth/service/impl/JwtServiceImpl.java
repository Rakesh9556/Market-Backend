package org.rakeshg.retailstore.auth.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.rakeshg.retailstore.auth.service.JwtService;
import org.rakeshg.retailstore.user.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {
    private final Key signingKey;

    private final long expirySeconds;

    public JwtServiceImpl(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.access-token-expiry-seconds}") long expirySeconds

    ) {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirySeconds = expirySeconds;
    }

    @Override
    public String generate(User user) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirySeconds * 1000);

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("name", user.getName())
                .claim("role", user.getRole().name())
                .claim("storeId", user.getStoreId())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Parse and validate token
    @Override
    public Claims parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
