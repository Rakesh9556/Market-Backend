package org.rakeshg.retailstore.auth.repository;

import org.rakeshg.retailstore.auth.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findBySelector(String selector);
    List<RefreshToken> findAllByUserIdAndRevokedFalse(Long userId);
}
