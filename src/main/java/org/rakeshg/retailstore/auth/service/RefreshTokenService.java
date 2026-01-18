package org.rakeshg.retailstore.auth.service;

import org.rakeshg.retailstore.auth.model.RefreshToken;
import org.rakeshg.retailstore.user.model.User;

public interface RefreshTokenService {
    String generate(User user);
    RefreshToken validateAndGet(String rawToken);
    String rotate(RefreshToken oldToken, User user);
    void revoke(String token);
    void revokeAllForUser(Long userId);
}
