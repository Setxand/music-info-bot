package com.chatbots.musicInfoBot.services.telegramService.impl;


import com.chatbots.musicInfoBot.entities.User;
import com.chatbots.musicInfoBot.enums.CallBackData;
import com.chatbots.musicInfoBot.enums.Role;
import com.chatbots.musicInfoBot.enums.speaker.Dictionary;
import com.chatbots.musicInfoBot.models.telegram.InputMedia;
import com.chatbots.musicInfoBot.models.telegram.Message;
import com.chatbots.musicInfoBot.models.telegram.PreCheckoutQuery;
import com.chatbots.musicInfoBot.models.telegram.TelegramRequest;
import com.chatbots.musicInfoBot.models.telegram.buttons.InlineKeyboardButton;
import com.chatbots.musicInfoBot.models.telegram.buttons.InlineKeyboardMarkup;
import com.chatbots.musicInfoBot.models.telegram.buttons.Markup;
import com.chatbots.musicInfoBot.services.repositoryService.DictionaryRepositoryService;
import com.chatbots.musicInfoBot.services.repositoryService.UserRepositoryService;
import com.chatbots.musicInfoBot.services.telegramService.TelegramMessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static com.chatbots.musicInfoBot.enums.CallBackData.*;
import static com.chatbots.musicInfoBot.enums.speaker.Buttons.*;
import static com.chatbots.musicInfoBot.enums.speaker.Dictionary.*;


@Service
public class TelegramMessageSenderServiceImpl implements TelegramMessageSenderService {
    @Autowired
    private UserRepositoryService userRepositoryService;
    @Autowired
    private DictionaryRepositoryService dictionaryRepositoryService;
    @Value("${url.telegram}")
    private String TELEGRAM_URL;
    @Value("${url.server}")
    private String SERVER_URL;

    @Override
    public void sendMessage(TelegramRequest telegramRequest) {
        try {
            String url = TELEGRAM_URL;
            new RestTemplate().postForEntity(url + "/sendMessage", telegramRequest, Void.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void helloMessage(Message message) {
        com.chatbots.musicInfoBot.entities.Dictionary dictionary = dictionaryRepositoryService.findById(HELLO_MESSAGE.name());
        String text = dictionary.getValue();
        String photoUrl = dictionary.getPhotoUrl();
        sendPhoto(photoUrl,text,null,message);
    }

    @Override
    public void simpleMessage(String message, Message m) {
        sendMessage(new TelegramRequest(message, m.getChat().getId()));
    }

    @Override
    public void errorMessage(Message message) {
        String text = "men, i don`t understand this command, try again)";
        sendMessage(new TelegramRequest(text, message.getChat().getId()));
    }

    @Override
    public void sendButtons(Markup markup, String text, Message message) {
        TelegramRequest telegramRequest = new TelegramRequest();
        telegramRequest.setChatId(message.getChat().getId());
        telegramRequest.setText(text);
        telegramRequest.setMarkup(markup);
        sendMessage(telegramRequest);
    }

    @Override
    public void sendInlineButtons(List<List<InlineKeyboardButton>> buttons, String text, Message message) {
        Markup markup = new InlineKeyboardMarkup(buttons);
        sendButtons(markup, text, message);
    }

    @Override
    public void sendPhoto(String photo, String caption, Markup markup, Message message) {
        String url = TELEGRAM_URL;
        new RestTemplate().postForEntity(url + "/sendPhoto", new TelegramRequest(message.getChat().getId(), markup, photo, caption), Void.class);


    }

    @Override
    public void sendActions(Message message) {
        List<List<InlineKeyboardButton>> inlineKeyboardButtons = new ArrayList<>();

        inlineKeyboardButtons.add(new ArrayList<>(Arrays.asList(new InlineKeyboardButton(ResourceBundle.getBundle("buttons").getString(VIDEO_BUTTON.name()), VIDEO_DATA.name()))));
        inlineKeyboardButtons.add(new ArrayList<>(Arrays.asList(new InlineKeyboardButton(ResourceBundle.getBundle("buttons").getString(CONCERTS_BUTTON.name()), CONCERTS_DATA.name()))));
        inlineKeyboardButtons.add(new ArrayList<>(Arrays.asList(new InlineKeyboardButton(ResourceBundle.getBundle("buttons").getString(GALLERY_BUTTON.name()), GALLERY_DATA.name()))));
        if (userRepositoryService.findByChatId(message.getChat().getId()) != null ) {
            User user = userRepositoryService.findByChatId(message.getChat().getId());
            if(user.getRole()== Role.ADMIN || user.getRole() == Role.MODERATOR)
            inlineKeyboardButtons.add(new ArrayList<>(Arrays.asList(new InlineKeyboardButton(ResourceBundle.getBundle("dictionary").getString(NEW_NEWS_SET.name()), NEW_NEWS_DATA.name()))));
        }
        String text = ResourceBundle.getBundle("dictionary").getString(CHOOSE_ACTIONS.name());
        sendInlineButtons(inlineKeyboardButtons, text, message);
    }

    @Override
    public void simpleQuestion(CallBackData data, String splitter, String text, Message message) {
        List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
        String yes = ResourceBundle.getBundle("dictionary").getString(YES.name());
        String no = ResourceBundle.getBundle("dictionary").getString(NO.name());
        inlineKeyboardButtons.add(new InlineKeyboardButton(yes, data.name() + splitter + QUESTION_YES.name()));
        inlineKeyboardButtons.add(new InlineKeyboardButton(no, data.name() + splitter + QUESTION_NO.name()));
        sendInlineButtons(new ArrayList<>(Arrays.asList(inlineKeyboardButtons)), text, message);
    }

    @Override
    public void noEnoughPermissions(Message message) {
        String text = "You have not enough permissions to make it!";
        simpleMessage(text, message);
    }

    @Override
    public void sendVideo(Message message, String url) {
        new RestTemplate().postForEntity(TELEGRAM_URL + "/sendVideo", new TelegramRequest(message.getChat().getId(), url), Void.class);
    }

    @Override
    public void sendGroupMedia(List<InputMedia> media, Message message) {
        TelegramRequest telegramRequest = new TelegramRequest();
        telegramRequest.setMedia(media);
        telegramRequest.setChatId(message.getChat().getId());
        new RestTemplate().postForEntity(TELEGRAM_URL + "/sendMediaGroup", telegramRequest, Void.class);
    }

    @Override
    public void sendInvoice(Message message, TelegramRequest telegramRequest) {
        new RestTemplate().postForEntity(TELEGRAM_URL + "/sendInvoice", telegramRequest, Void.class);
    }

    @Override
    public void sendPreCheckoutQuery(PreCheckoutQuery preCheckoutQuery) {
        String url = TELEGRAM_URL+"/answerPreCheckoutQuery?pre_checkout_query_id="+preCheckoutQuery.getId()+"&ok="+true;
        new RestTemplate().getForEntity(url,Void.class);
    }


}
