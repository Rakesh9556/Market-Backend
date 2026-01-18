package org.rakeshg.retailstore.store.store.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "stores",
        indexes = {
                @Index(name = "idx_store_phone", columnList = "phone")
        },
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "phone"),
                @UniqueConstraint(columnNames = "email")
        }
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Boolean active;

}
