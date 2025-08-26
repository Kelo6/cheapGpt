package com.cheappool.web;

import com.cheappool.domain.SubAccount;
import com.cheappool.service.AccountPoolService;
import com.cheappool.service.ChatService;
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

    @GetMapping("/chat")
    public String chatPage(Model model, Principal principal) {
        SubAccount subAccount = accountPoolService.pickNextAvailable();
        model.addAttribute("subAccount", subAccount);
        model.addAttribute("username", principal.getName());
        return "chat";
    }

    @PostMapping("/chat/send")
    @ResponseBody
    public Map<String, Object> sendMessage(@RequestParam String message, Principal principal) {
        SubAccount subAccount = accountPoolService.pickNextAvailable();
        if (subAccount == null) {
            return Map.of("error", "暂无可用的子账号");
        }
        
        String response = chatService.sendToChatGPT(subAccount, message, principal.getName());
        return Map.of(
            "success", true,
            "response", response,
            "subAccountKey", subAccount.getKey()
        );
    }
}
