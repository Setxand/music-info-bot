package com.chatbots.musicInfoBot.services.telegramService;

import com.chatbots.musicInfoBot.models.telegram.CallBackQuery;
import com.chatbots.musicInfoBot.models.telegram.Message;
import com.chatbots.musicInfoBot.models.telegram.PreCheckoutQuery;

public interface HelperService {
    public void helpStartBotCommand(Message message);
    public void helpVideoDataCallBack(CallBackQuery callBackQuery);
    public void helpNewNewsDataCallBack(CallBackQuery callBackQuery);
    public void helpSavePhoto(Message message);
    public void galleryDataCallBack(CallBackQuery callBackQuery);
    public void helpConcertsDataCallBack(CallBackQuery callBackQuery);
    public void helpBuyTicketsCallBack(CallBackQuery callBackQuery);
    public void helpPreCheckoutQueryHandle(PreCheckoutQuery preCheckoutQuery);
}
