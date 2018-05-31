package com.chatbots.musicInfoBot.services.telegramService.impl;

import com.chatbots.musicInfoBot.entities.Photo;
import com.chatbots.musicInfoBot.entities.User;
import com.chatbots.musicInfoBot.models.telegram.Message;
import com.chatbots.musicInfoBot.models.telegram.TelegramEntity;
import com.chatbots.musicInfoBot.services.repositoryService.PhotoIdRepositoryService;
import com.chatbots.musicInfoBot.services.repositoryService.UserRepositoryService;
import com.chatbots.musicInfoBot.services.telegramService.BotCommandParserService;
import com.chatbots.musicInfoBot.services.telegramService.HelperService;
import com.chatbots.musicInfoBot.services.telegramService.MessageParserService;
import com.chatbots.musicInfoBot.services.telegramService.TelegramMessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ResourceBundle;

import static com.chatbots.musicInfoBot.enums.Status.ENTERING_PICTURE_STATUS;
import static com.chatbots.musicInfoBot.enums.speaker.Dictionary.WRONG_DATA_ENTERING;

@Service
public class MessageParserServiceImpl implements MessageParserService {
    @Autowired
    private BotCommandParserService botCommandParserService;
    @Autowired
    private HelperService helperService;
    @Autowired
    private UserRepositoryService userRepositoryService;
    @Autowired
    private PhotoIdRepositoryService photoIdRepositoryService;
    @Autowired
    private TelegramMessageSenderService telegramMessageSenderService;

    @Override
    public void parseMessage(Message message) {
        User user = userRepositoryService.findByChatId(message.getChat().getId());

        if(message.getEntities()!=null){
            for(TelegramEntity telegramEntity: message.getEntities()){
                if(telegramEntity.getType().equals("bot_command")){
                    botCommandParserService.parseBotCommand(message);
                    return;
                }
            }
        }

        if(message.getPhoto()!=null && user.getStatus()==ENTERING_PICTURE_STATUS){
            helperService.helpSavePhoto(message);
            return;
        }

        if(message.getSuccessfulPayment()!=null){
            helperService.successfulPayment(message);
            return;
        }
        if(user.getStatus()==null){
            helperService.helpMessageCommand(message);
            return;
        }
        switch (user.getStatus()){
            case ENTERING_PICTURE_STATUS:
                enteringPictureStatus(message);
                break;
                default:
                    telegramMessageSenderService.errorMessage(message);
                    break;
        }
    }

    private void enteringPictureStatus(Message message) {

            String text = message.getText();
            Photo photo = photoIdRepositoryService.findTop();
            User user = userRepositoryService.findByChatId(message.getChat().getId());
            List<User> users = userRepositoryService.findAll();
        try {
            for(User currentUser :users){
                message.getChat().setId(currentUser.getChatId());
                telegramMessageSenderService.sendPhoto(photo.getFileId(),text,null,message);
                telegramMessageSenderService.simpleMessage("/help",message);
            }
            userRepositoryService.changeStatus(user,null);
            photoIdRepositoryService.delete(photo);

        }
        catch (Exception ex){
            String mesText = ResourceBundle.getBundle("dictionary").getString(WRONG_DATA_ENTERING.name());
            telegramMessageSenderService.simpleMessage(mesText,message);
        }

    }
}
