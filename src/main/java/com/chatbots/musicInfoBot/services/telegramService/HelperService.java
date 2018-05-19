package com.chatbots.musicInfoBot.services.telegramService;

import com.chatbots.musicInfoBot.models.telegram.CallBackQuery;
import com.chatbots.musicInfoBot.models.telegram.Message;
import com.chatbots.musicInfoBot.models.telegram.PreCheckoutQuery;

public interface HelperService {
    void helpStartBotCommand(Message message);
    void helpVideoDataCallBack(CallBackQuery callBackQuery);
    void helpNewNewsDataCallBack(CallBackQuery callBackQuery);
    void helpSavePhoto(Message message);
    void galleryDataCallBack(CallBackQuery callBackQuery);
    void helpConcertsDataCallBack(CallBackQuery callBackQuery);
    void helpBuyTicketsCallBack(CallBackQuery callBackQuery);
    void helpPreCheckoutQueryHandle(PreCheckoutQuery preCheckoutQuery);
    void successfulPayment(Message message);
    void helpMessageCommand(Message message);
}
