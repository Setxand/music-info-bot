package com.chatbots.musicInfoBot.services.telegramService;

import com.chatbots.musicInfoBot.models.telegram.Message;

public interface BotCommandParserService {
    public void parseBotCommand(Message message);
}
