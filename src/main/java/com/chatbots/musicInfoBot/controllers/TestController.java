package com.chatbots.musicInfoBot.controllers;

import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Setter
@RequestMapping("/test")
public class TestController {
    private Object object;
    @GetMapping
    public Object test(){
        return object;
    }
}
