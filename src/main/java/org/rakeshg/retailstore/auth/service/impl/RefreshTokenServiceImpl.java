package org.rakeshg.retailstore.auth.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.rakeshg.retailstore.auth.model.RefreshToken;
import org.rakeshg.retailstore.auth.repository.RefreshTokenRepository;
import org.rakeshg.retailstore.auth.service.RefreshTokenService;
import org.rakeshg.retailstore.user.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${security.refresh-token-valid-days}")
    private static int REFRESH_TOKEN_VALID_DAYS;

    private final BCryptPasswordEncoder encoder;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public String generate(User user) {
        String selector = UUID.randomUUID().toString();
        String verifier = UUID.randomUUID().toString();

        RefreshToken token = RefreshToken.builder()
                .userId(user.getId())
                .storeId(user.getStoreId())
                .selector(selector)
                .hashedVerifier(encoder.encode(verifier))
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(REFRESH_TOKEN_VALID_DAYS))
                .rotated(false)
                .revoked(false)
                .build();

        refreshTokenRepository.save(token);
        return selector + "." + verifier;
    }


    @Override
    public RefreshToken validateAndGet(String rawToken) {
        String[] parts = rawToken.split("\\.");

        if(parts.length != 2) {
            throw new SecurityException("Invalid refresh token format");
        }

        String selector = parts[0];
        String verifier = parts[1];

        RefreshToken token = refreshTokenRepository.findBySelector(selector)
                .orElseThrow(() -> new SecurityException("Invalid refresh token"));

        if(Boolean.TRUE.equals(token.getRevoked())) {
            throw new SecurityException("Refresh token revoked");
        }

        if(Boolean.TRUE.equals(token.getRotated())) {
            throw new SecurityException("Refresh token reuse detected");
        }

        if(token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new SecurityException("Refresh token expired");
        }

        if(!encoder.matches(verifier, token.getHashedVerifier())) {
            throw new SecurityException("Invalid refresh token");
        }

        return token;
    }

    @Transactional
    @Override
    public String rotate(RefreshToken oldToken, User user) {
        oldToken.setRotated(true);
        refreshTokenRepository.save(oldToken);
        return generate(user);
    }

    @Override
    public void revoke(String rawToken) {

        if(rawToken == null || rawToken.isBlank()) return;

        String[] parts = rawToken.split("\\.");
        if(parts.length != 2) return;

        String selector = parts[0];
        refreshTokenRepository.findBySelector(selector)
                .ifPresent(token -> {
                    if(!Boolean.TRUE.equals(token.getRevoked())) {
                        token.setRevoked(true);
                        refreshTokenRepository.save(token);
                    }
                });
    }

    @Override
    public void revokeAllForUser(Long userId) {

        // Load all active tokens
        List<RefreshToken> activeTokens = refreshTokenRepository.findAllByUserIdAndRevokedFalse(userId);

        // Set revoked true for all
        for(RefreshToken token: activeTokens) {
            token.setRevoked(true);
        }

        // commit the changes
        refreshTokenRepository.saveAll(activeTokens);

    }
}
