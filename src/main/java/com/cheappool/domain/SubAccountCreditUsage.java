package com.cheappool.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Table(name = "sub_account_credit_usage")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubAccountCreditUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_account_id", nullable = false)
    private SubAccount subAccount;

    @Column(nullable = false)
    private int points;

    @Column(nullable = false)
    private OffsetDateTime createdAt;
}


