package org.rakeshg.retailstore.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "otp_sessions",
        indexes = {
                @Index(name = "idx_otp_phone", columnList = "phone")
        }
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OtpSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String hashedOtp;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private Boolean used;
}
