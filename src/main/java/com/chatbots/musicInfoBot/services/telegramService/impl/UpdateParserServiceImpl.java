package com.chatbots.musicInfoBot.services.telegramService.impl;

import com.chatbots.musicInfoBot.models.telegram.Update;

import com.chatbots.musicInfoBot.services.telegramService.CallBackParserService;
import com.chatbots.musicInfoBot.services.telegramService.MessageParserService;
import com.chatbots.musicInfoBot.services.telegramService.TelegramMessageSenderService;
import com.chatbots.musicInfoBot.services.telegramService.UpdateParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateParserServiceImpl implements UpdateParserService {
    @Autowired
    private MessageParserService messageParserService;
    @Autowired
    private CallBackParserService callBackParserService;
    @Autowired
    private TelegramMessageSenderService telegramMessageSenderService;

    @Override
    public void parseUpdate(Update update) {
        try {
            if (update.getCallBackQuery() != null) {
                callBackParserService.parseCallBackQuery(update.getCallBackQuery());
            } else if (update.getMessage() != null) {
                messageParserService.parseMessage(update.getMessage());
            }
        } catch (Exception ex) {
            try {
                telegramMessageSenderService.errorMessage(update.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }

            ex.printStackTrace();
        }

    }
}
