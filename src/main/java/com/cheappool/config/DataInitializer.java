package com.cheappool.config;

import com.cheappool.domain.AppUser;
import com.cheappool.domain.EnterpriseAccount;
import com.cheappool.domain.SubAccount;
import com.cheappool.repository.AppUserRepository;
import com.cheappool.repository.EnterpriseAccountRepository;
import com.cheappool.repository.SubAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.OffsetDateTime;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData(AppUserRepository users,
                                      EnterpriseAccountRepository enterprises,
                                      SubAccountRepository subs) {
        return args -> {
            OffsetDateTime now = OffsetDateTime.now();

            if (users.count() == 0) {
                users.save(AppUser.builder()
                        .username("admin")
                        .passwordHash(passwordEncoder.encode("admin123"))
                        .admin(true)
                        .createdAt(now)
                        .updatedAt(now)
                        .build());
            }

            if (enterprises.count() == 0) {
                // 先创建企业账号
                EnterpriseAccount ent = EnterpriseAccount.builder()
                        .name("ent-1")
                        .organizationId("org-001")
                        .credential("placeholder")
                        .enabled(true)
                        .note("demo enterprise")
                        .createdAt(now)
                        .updatedAt(now)
                        .build();
                ent = enterprises.save(ent);

                // 再创建子账号
                SubAccount sub = SubAccount.builder()
                        .enterpriseAccount(ent)
                        .key("sub-1")
                        .credential("placeholder")
                        .enabled(true)
                        .concurrentLimit(5)
                        .createdAt(now)
                        .updatedAt(now)
                        .build();
                subs.save(sub);
            }
        };
    }
}


