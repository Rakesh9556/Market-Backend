package org.rakeshg.retailstore.auth.repository;

import org.rakeshg.retailstore.auth.model.OtpSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OtpSessionRepository extends JpaRepository<OtpSession, Long> {
    Optional<OtpSession> findTopByPhoneAndUsedFalseOrderByExpiresAtDesc(String phone);
    long countByPhoneAndUsedFalseAndExpiresAtAfter(String phone, LocalDateTime now);
}
