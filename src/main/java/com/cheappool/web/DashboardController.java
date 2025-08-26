package com.cheappool.web;

import com.cheappool.domain.SubAccount;
import com.cheappool.repository.SubAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final SubAccountRepository subAccountRepository;

    @GetMapping("/")
    public String index(Model model) {
        List<SubAccount> subs = subAccountRepository.findAll();
        model.addAttribute("subAccounts", subs);
        return "dashboard";
    }
}


