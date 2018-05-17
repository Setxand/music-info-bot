package com.chatbots.musicInfoBot.services.telegramService.impl;

import com.chatbots.musicInfoBot.entities.PhotoId;
import com.chatbots.musicInfoBot.entities.User;
import com.chatbots.musicInfoBot.models.telegram.CallBackQuery;
import com.chatbots.musicInfoBot.models.telegram.Message;
import com.chatbots.musicInfoBot.repositories.PhotoIdRepository;
import com.chatbots.musicInfoBot.services.repositoryService.PhotoIdRepositoryService;
import com.chatbots.musicInfoBot.services.repositoryService.UserRepositoryService;
import com.chatbots.musicInfoBot.services.telegramService.HelperService;
import com.chatbots.musicInfoBot.services.telegramService.TelegramMessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ResourceBundle;

import static com.chatbots.musicInfoBot.enums.Status.ENTERING_PICTURE_STATUS;
import static com.chatbots.musicInfoBot.enums.speaker.Dictionary.ENTER_PICTURE;
import static com.chatbots.musicInfoBot.enums.speaker.Dictionary.ENTER_TEXT;

@Service
public class HelperServiceImpl implements HelperService {
    @Autowired
    private TelegramMessageSenderService messageSenderService;
    @Autowired
    private UserRepositoryService userRepositoryService;
    @Autowired
    private PhotoIdRepositoryService photoIdRepositoryService;
    @Override
    public void helpStartBotCommand(Message message) {

        if(userRepositoryService.findByChatId(message.getChat().getId())==null){
            User user = new User(message.getChat().getId());
            userRepositoryService.saveAndFlush(user);

        }


        messageSenderService.helloMessage(message);
        messageSenderService.sendActions(message);
    }

    @Override
    public void helpVideoDataCallBack(CallBackQuery callBackQuery) {
        String url = "https://www.youtube.com/watch?v=L4WXNlupNpw";
        messageSenderService.simpleMessage(url,callBackQuery.getMessage());
    }

    @Override
    public void helpNewNewsDataCallBack(CallBackQuery callBackQuery) {
        String text = ResourceBundle.getBundle("dictionary").getString(ENTER_PICTURE.name());
        User user = userRepositoryService.findByChatId(callBackQuery.getMessage().getChat().getId());
        userRepositoryService.changeStatus(user,ENTERING_PICTURE_STATUS);
        messageSenderService.simpleMessage(text,callBackQuery.getMessage());
    }

    @Override
    public void helpSavePhoto(Message message) {
        String fileId = message.getPhoto().get(message.getPhoto().size()-1).getFileId();
        photoIdRepositoryService.saveAndFlush(new PhotoId(fileId));
        String text = ResourceBundle.getBundle("dictionary").getString(ENTER_TEXT.name());
        messageSenderService.simpleMessage(text,message);
    }
}
