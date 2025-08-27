package com.cheappool.web;

import com.cheappool.domain.SubAccount;
import com.cheappool.service.AccountPoolService;
import com.cheappool.service.ChatService;
import com.cheappool.service.QuotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final AccountPoolService accountPoolService;
    private final ChatService chatService;
    private final QuotaService quotaService;

    @GetMapping("/chat")
    public String chatPage(Model model, Principal principal) {
        SubAccount subAccount = accountPoolService.pickNextAvailable();
        model.addAttribute("subAccount", subAccount);
        model.addAttribute("username", principal.getName());
        
        // 添加积分信息
        if (subAccount != null) {
            int used = quotaService.getUsedInWindow(subAccount, java.time.OffsetDateTime.now());
            int remaining = quotaService.getRemaining(subAccount, java.time.OffsetDateTime.now());
            model.addAttribute("creditsUsed", used);
            model.addAttribute("creditsRemaining", remaining);
            model.addAttribute("creditsTotal", subAccount.getWindowLimit());
            model.addAttribute("costPerMessage", subAccount.getCostPerMessage());
            model.addAttribute("windowHours", subAccount.getWindowSeconds() / 3600);
        }
        
        return "chat";
    }

    @PostMapping("/chat/send")
    @ResponseBody
    public Map<String, Object> sendMessage(@RequestParam String message, Principal principal) {
        SubAccount subAccount = accountPoolService.pickNextAvailable();
        if (subAccount == null) {
            return Map.of("error", "暂无可用的子账号");
        }
        
        int cost = subAccount.getCostPerMessage();
        boolean ok = quotaService.consumeIfAllowed(subAccount, cost, principal.getName());
        if (!ok) {
            return Map.of(
                "success", false,
                "error", "积分不足，请稍后再试",
                "remaining", 0
            );
        }

        String response = chatService.sendToChatGPT(subAccount, message, principal.getName());
        
        // 获取最新的积分信息
        int used = quotaService.getUsedInWindow(subAccount, java.time.OffsetDateTime.now());
        int remaining = quotaService.getRemaining(subAccount, java.time.OffsetDateTime.now());
        
        return Map.of(
            "success", true,
            "response", response,
            "subAccountKey", subAccount.getKey(),
            "creditsUsed", used,
            "creditsRemaining", remaining,
            "creditsTotal", subAccount.getWindowLimit()
        );
    }
}
