package com.bit.yourmain.controller.api;

import com.bit.yourmain.domain.users.Users;
import com.bit.yourmain.service.MailService;
import com.bit.yourmain.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MailApiController {

    private final MailService mailService;
    private final UsersService usersService;

    @PostMapping("/emailSend")
    public Boolean emailSend(HttpSession session, @RequestBody Map<String,String> map) {
        Boolean result = false;
        String userEmail = map.get("userEmail");
        Users users = null;
        try {
            users = usersService.findByEmail(userEmail);
        } catch (Exception e) {
            e.getMessage();
        }
        if (users == null) {
            mailService.mailSend(session, userEmail);
            result = true;
        } else if (map.get("find").equals("find")) {
            mailService.mailSend(session, userEmail);
            result = true;
        }
        return result;
    }

    @PostMapping("/emailCheck")
    public boolean emailCheck(HttpServletRequest request, @RequestBody Map<String, String> map) {
        HttpSession session = request.getSession();
        String Code = map.get("inputCode");
        int inputCode = 999999999;

        try {
            inputCode = Integer.parseInt(Code);
        } catch (Exception e) {
            System.out.println("code is not integer");
        }

        boolean result = mailService.emailAuth(session, inputCode);

        return result;
    }
}
