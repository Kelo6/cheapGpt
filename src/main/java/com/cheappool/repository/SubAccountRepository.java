package com.cheappool.repository;

import com.cheappool.domain.SubAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubAccountRepository extends JpaRepository<SubAccount, Long> {
    List<SubAccount> findByEnabledTrueOrderByIdAsc();
}


