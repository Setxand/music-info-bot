package com.chatbots.musicInfoBot.services.telegramService;

import com.chatbots.musicInfoBot.models.telegram.Message;

public interface MessageParserService {
    public void parseMessage(Message message);
}
