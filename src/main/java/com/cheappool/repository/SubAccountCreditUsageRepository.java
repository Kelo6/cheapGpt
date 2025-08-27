package com.cheappool.repository;

import com.cheappool.domain.SubAccountCreditUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;

public interface SubAccountCreditUsageRepository extends JpaRepository<SubAccountCreditUsage, Long> {

    @Query("select coalesce(sum(u.points), 0) from SubAccountCreditUsage u where u.subAccount.id = :subId and u.createdAt >= :cutoff")
    Integer sumPointsSince(@Param("subId") Long subAccountId, @Param("cutoff") OffsetDateTime cutoff);
}


