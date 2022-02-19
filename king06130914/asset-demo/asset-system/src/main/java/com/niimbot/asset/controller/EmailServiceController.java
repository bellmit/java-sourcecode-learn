package com.niimbot.asset.controller;

import com.niimbot.asset.system.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 邮箱控制器
 *
 * @author dk
 */
@RestController
@RequestMapping("server/system/email")
@RequiredArgsConstructor
public class EmailServiceController {

    private final EmailService emailService;

    @GetMapping("/send")
    public Boolean sendEmail(@RequestParam(value = "email") String email) {
        return emailService.sendEmail(email);
    }
}
