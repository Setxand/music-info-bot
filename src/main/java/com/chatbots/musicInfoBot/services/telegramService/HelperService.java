package com.chatbots.musicInfoBot.services.telegramService;

import com.chatbots.musicInfoBot.models.telegram.CallBackQuery;
import com.chatbots.musicInfoBot.models.telegram.Message;

public interface HelperService {
    public void helpStartBotCommand(Message message);
    public void helpVideoDataCallBack(CallBackQuery callBackQuery);
    public void helpNewNewsDataCallBack(CallBackQuery callBackQuery);
    public void helpSavePhoto(Message message);
}
