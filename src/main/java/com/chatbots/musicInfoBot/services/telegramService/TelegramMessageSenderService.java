package com.chatbots.musicInfoBot.services.telegramService;



import com.chatbots.musicInfoBot.enums.CallBackData;
import com.chatbots.musicInfoBot.models.telegram.InputMedia;
import com.chatbots.musicInfoBot.models.telegram.PreCheckoutQuery;
import com.chatbots.musicInfoBot.models.telegram.payment.Invoice;
import com.chatbots.musicInfoBot.models.telegram.Message;
import com.chatbots.musicInfoBot.models.telegram.TelegramRequest;
import com.chatbots.musicInfoBot.models.telegram.buttons.InlineKeyboardButton;
import com.chatbots.musicInfoBot.models.telegram.buttons.Markup;

import java.util.List;

public interface TelegramMessageSenderService {
    public void sendMessage(TelegramRequest telegramRequest);
    public void helloMessage(Message message);
    public void simpleMessage(String message, Message m);
    public void errorMessage(Message message);
    public void sendButtons(Markup markup, String text, Message message);
    public void sendInlineButtons(List<List<InlineKeyboardButton>> buttons, String text, Message message);
    public void sendPhoto(String photo, String caption, Markup markup, Message message);
    public void sendActions(Message message);
    public void simpleQuestion(CallBackData data, String splitter, String text, Message message);
    public void noEnoughPermissions(Message message);
    public void sendVideo(Message message, String url);
    public void sendGroupMedia(List<InputMedia>media,Message message);
    public void sendInvoice(Message message, TelegramRequest telegramRequest);
    public void sendPreCheckoutQuery(PreCheckoutQuery preCheckoutQuery);
}
