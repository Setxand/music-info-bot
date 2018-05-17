package com.chatbots.musicInfoBot.services.telegramService.impl;

import com.chatbots.musicInfoBot.enums.BotCommands;
import com.chatbots.musicInfoBot.models.telegram.Message;
import com.chatbots.musicInfoBot.services.telegramService.BotCommandParserService;
import com.chatbots.musicInfoBot.services.telegramService.HelperService;
import com.chatbots.musicInfoBot.services.telegramService.TelegramMessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BotCommandParserServiceImpl implements BotCommandParserService {
    @Autowired
    private TelegramMessageSenderService telegramMessageSenderService;
    @Autowired
    private HelperService helperService;
    @Override
    public void parseBotCommand(Message message) {
        StringBuilder command = new StringBuilder(message.getText()).deleteCharAt(0);

        switch (BotCommands.valueOf(command.toString().toUpperCase())){
            case START:
                start(message);
                break;
                default:
                    telegramMessageSenderService.errorMessage(message);
                    break;
        }
    }

    private void start(Message message) {
        helperService.helpStartBotCommand(message);
    }
}
