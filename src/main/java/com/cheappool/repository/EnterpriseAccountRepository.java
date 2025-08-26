package com.cheappool.repository;

import com.cheappool.domain.EnterpriseAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnterpriseAccountRepository extends JpaRepository<EnterpriseAccount, Long> {
    Optional<EnterpriseAccount> findByName(String name);
}


