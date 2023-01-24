package com.example.secondteamproject.chatting;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class chatcontroller {


    @GetMapping("/chat")
    public String chatGET(HttpSession session) {

        System.out.println("@ChatController, chat GET()");

        return "chat";
    }
}
