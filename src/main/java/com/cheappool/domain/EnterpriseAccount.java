package com.cheappool.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Table(name = "enterprise_accounts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 100)
    private String organizationId;

    @Column(length = 2048)
    private String credential;

    @Column(nullable = false)
    private boolean enabled;

    @Column(length = 500)
    private String note;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private OffsetDateTime updatedAt;
}


