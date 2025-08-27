package com.cheappool.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Table(name = "sub_accounts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "enterprise_account_id", nullable = false)
    private EnterpriseAccount enterpriseAccount;

    @Column(name = "sub_key", nullable = false, unique = true, length = 100)
    private String key;

    @Column(length = 2048)
    private String credential;

    @Column(nullable = false)
    private boolean enabled;

    @Column(nullable = false)
    private int concurrentLimit;

    /**
     * 时间窗口内可用的最大积分（配额上限）
     */
    @Column(nullable = false)
    private int windowLimit;

    /**
     * 时间窗口大小（秒）
     */
    @Column(nullable = false)
    private int windowSeconds;

    /**
     * 每次对话消耗的积分
     */
    @Column(nullable = false)
    private int costPerMessage;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private OffsetDateTime updatedAt;
}


