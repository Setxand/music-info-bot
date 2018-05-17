package com.chatbots.musicInfoBot.controllers;

import com.chatbots.musicInfoBot.models.telegram.Update;
import com.chatbots.musicInfoBot.services.telegramService.UpdateParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/telegramWebHook")
public class WebhookController {
    @Autowired
    private UpdateParserService updateParserService;
    @PostMapping
    public void doPost(@RequestBody Update update){
        updateParserService.parseUpdate(update);
    }
}
