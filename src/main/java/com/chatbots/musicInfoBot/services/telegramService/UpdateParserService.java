package com.chatbots.musicInfoBot.services.telegramService;

import com.chatbots.musicInfoBot.models.telegram.Update;

public interface UpdateParserService {
    public void parseUpdate(Update update);
}
