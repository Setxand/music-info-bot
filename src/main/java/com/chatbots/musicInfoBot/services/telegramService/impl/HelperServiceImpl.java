package com.chatbots.musicInfoBot.services.telegramService.impl;

import com.chatbots.musicInfoBot.entities.Concert;
import com.chatbots.musicInfoBot.entities.Gallery;
import com.chatbots.musicInfoBot.entities.Photo;
import com.chatbots.musicInfoBot.entities.User;
import com.chatbots.musicInfoBot.models.telegram.*;
import com.chatbots.musicInfoBot.models.telegram.buttons.InlineKeyboardButton;
import com.chatbots.musicInfoBot.models.telegram.buttons.InlineKeyboardMarkup;
import com.chatbots.musicInfoBot.models.telegram.buttons.Markup;
import com.chatbots.musicInfoBot.services.repositoryService.ConcertRepositoryService;
import com.chatbots.musicInfoBot.services.repositoryService.GalleryRepositoryService;
import com.chatbots.musicInfoBot.services.repositoryService.PhotoIdRepositoryService;
import com.chatbots.musicInfoBot.services.repositoryService.UserRepositoryService;
import com.chatbots.musicInfoBot.services.telegramService.HelperService;
import com.chatbots.musicInfoBot.services.telegramService.TelegramMessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static com.chatbots.musicInfoBot.enums.CallBackData.BUY_TICKETS_DATA;
import static com.chatbots.musicInfoBot.enums.MediaTypes.VIDEO;
import static com.chatbots.musicInfoBot.enums.Status.ENTERING_PICTURE_STATUS;
import static com.chatbots.musicInfoBot.enums.speaker.Buttons.BUY_TICKET_BUTTON;
import static com.chatbots.musicInfoBot.enums.speaker.Dictionary.*;

@Service
public class HelperServiceImpl implements HelperService {
    @Autowired
    private TelegramMessageSenderService messageSenderService;
    @Autowired
    private UserRepositoryService userRepositoryService;
    @Autowired
    private PhotoIdRepositoryService photoIdRepositoryService;
    @Autowired
    private GalleryRepositoryService galleryRepositoryService;
    @Autowired
    private ConcertRepositoryService concertRepositoryService;
    @Override
    public void helpStartBotCommand(Message message) {

        if (userRepositoryService.findByChatId(message.getChat().getId()) == null) {
            User user = new User(message.getChat().getId());
            userRepositoryService.saveAndFlush(user);

        }


        messageSenderService.helloMessage(message);
        messageSenderService.sendActions(message);
    }

    @Override
    public void helpVideoDataCallBack(CallBackQuery callBackQuery) {
        List<Gallery>gallery = galleryRepositoryService.findListOfGallery(VIDEO.name().toLowerCase());
        String textMes = ResourceBundle.getBundle("dictionary").getString(NEWEST_VIDEOS.name());
        messageSenderService.simpleMessage(textMes,callBackQuery.getMessage());
        for(Gallery g:gallery){
            StringBuilder text = new StringBuilder();
            text.append(g.getCaption()+"\n\n"+g.getMedia());
            messageSenderService.simpleMessage(text.toString(), callBackQuery.getMessage());
        }
        messageSenderService.sendActions(callBackQuery.getMessage());

    }

    @Override
    public void helpNewNewsDataCallBack(CallBackQuery callBackQuery) {
        String text = ResourceBundle.getBundle("dictionary").getString(ENTER_PICTURE.name());
        User user = userRepositoryService.findByChatId(callBackQuery.getMessage().getChat().getId());
        userRepositoryService.changeStatus(user, ENTERING_PICTURE_STATUS);
        messageSenderService.simpleMessage(text, callBackQuery.getMessage());
    }

    @Override
    public void helpSavePhoto(Message message) {
        String fileId = message.getPhoto().get(message.getPhoto().size() - 1).getFileId();
        photoIdRepositoryService.saveAndFlush(new Photo(fileId));
        String text = ResourceBundle.getBundle("dictionary").getString(ENTER_TEXT.name());
        messageSenderService.simpleMessage(text, message);
    }

    @Override
    public void galleryDataCallBack(CallBackQuery callBackQuery) {
        List<Gallery> gallery = galleryRepositoryService.findListOfGallery("photo");
        List<InputMedia> inputMedia = new ArrayList<>();
        String text = ResourceBundle.getBundle("dictionary").getString(PHOTO_GALLERY.name());
        messageSenderService.simpleMessage(text,callBackQuery.getMessage());
        sendingMediaGroup(callBackQuery, gallery, inputMedia);
        messageSenderService.sendActions(callBackQuery.getMessage());
    }

    @Override
    public void helpConcertsDataCallBack(CallBackQuery callBackQuery) {
        List<Concert>concerts = concertRepositoryService.findAll();
        String buttonText = ResourceBundle.getBundle("buttons").getString(BUY_TICKET_BUTTON.name());
        Markup markup;
        for(Concert concert:concerts){
            String caption = concert.getCity()+", "+concert.getCountry()+"\n"+concert.getLocation()+"\n"+concert.getDateTime();
            markup = new InlineKeyboardMarkup(Arrays.asList(Arrays.asList(new InlineKeyboardButton(buttonText,BUY_TICKETS_DATA.name()))));
            messageSenderService.sendPhoto(concert.getPhoto(),caption,markup,callBackQuery.getMessage());
        }
        messageSenderService.sendActions(callBackQuery.getMessage());
    }

    @Override
    public void helpBuyTicketsCallBack(CallBackQuery callBackQuery) {
        TelegramRequest telegramRequest = new TelegramRequest();
        telegramRequest.setCurrency("USD");
        telegramRequest.setTitle("music info bot");
        telegramRequest.setChatId(callBackQuery.getMessage().getChat().getId());
        telegramRequest.setDescription("Desc");
        telegramRequest.setPayload("payload");

    }

    private void sendingMediaGroup(CallBackQuery callBackQuery, List<Gallery> gallery, List<InputMedia> inputMedia) {
        int i = 0;
        while (true) {
            List<Gallery> subList;
            try {
                subList = gallery.subList(i, i + 10);
            } catch (Exception ex) {
                break;
            }

            for (Gallery gall : subList) {
                inputMedia.add(new InputMediaPhoto(gall.getType(), gall.getMedia(), gall.getCaption()));
            }

            messageSenderService.sendGroupMedia(inputMedia, callBackQuery.getMessage());
            i += 10;
            inputMedia.clear();
        }
    }
}
