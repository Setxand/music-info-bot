package com.chatbots.musicInfoBot.services.telegramService;


import com.chatbots.musicInfoBot.models.telegram.CallBackQuery;

public interface CallBackParserService {
    public void parseCallBackQuery(CallBackQuery callBackQuery);
}
