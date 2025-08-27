package com.cheappool.service;

import com.cheappool.domain.SubAccount;
import com.cheappool.domain.SubAccountCreditUsage;
import com.cheappool.repository.SubAccountCreditUsageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuotaService {

    private final SubAccountCreditUsageRepository usageRepository;

    public int getUsedInWindow(SubAccount subAccount, OffsetDateTime now) {
        OffsetDateTime cutoff = now.minusSeconds(Math.max(subAccount.getWindowSeconds(), 0));
        Integer used = usageRepository.sumPointsSince(subAccount.getId(), cutoff);
        return used == null ? 0 : used;
    }

    public int getRemaining(SubAccount subAccount, OffsetDateTime now) {
        int used = getUsedInWindow(subAccount, now);
        return Math.max(subAccount.getWindowLimit() - used, 0);
    }

    @Transactional
    public boolean consumeIfAllowed(SubAccount subAccount, int cost, String username) {
        OffsetDateTime now = OffsetDateTime.now();
        int remaining = getRemaining(subAccount, now);
        if (cost <= remaining) {
            SubAccountCreditUsage usage = SubAccountCreditUsage.builder()
                    .subAccount(subAccount)
                    .points(cost)
                    .createdAt(now)
                    .build();
            usageRepository.save(usage);
            log.info("用户 {} 消耗积分 {}，子账号 {} 剩余 {} / {} (窗口 {}s)", username, cost, subAccount.getKey(), remaining - cost, subAccount.getWindowLimit(), subAccount.getWindowSeconds());
            return true;
        }
        log.warn("用户 {} 尝试消耗积分 {} 失败，子账号 {} 剩余 {} / {} (窗口 {}s)", username, cost, subAccount.getKey(), remaining, subAccount.getWindowLimit(), subAccount.getWindowSeconds());
        return false;
    }
}


