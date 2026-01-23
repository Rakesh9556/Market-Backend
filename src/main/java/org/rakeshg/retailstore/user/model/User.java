package org.rakeshg.retailstore.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rakeshg.retailstore.common.enums.UserRole;

@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_users_store_id", columnList = "store_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "phone"),
        }
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column
    private String name;

    @Column(name = "store_id")
    private Long storeId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(nullable = false)
    private Boolean active;
}
