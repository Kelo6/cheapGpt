package com.cheappool.service;

import com.cheappool.domain.SubAccount;
import com.cheappool.repository.SubAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class AccountPoolService {
    private final SubAccountRepository subAccountRepository;
    private final AtomicInteger roundRobinIndex = new AtomicInteger(0);

    public SubAccount pickNextAvailable() {
        List<SubAccount> enabled = subAccountRepository.findByEnabledTrueOrderByIdAsc();
        if (enabled.isEmpty()) {
            return null;
        }
        int idx = Math.floorMod(roundRobinIndex.getAndIncrement(), enabled.size());
        return enabled.get(idx);
    }
}


