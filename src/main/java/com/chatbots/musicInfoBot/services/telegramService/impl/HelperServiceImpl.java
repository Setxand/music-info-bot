package com.chatbots.musicInfoBot.services.telegramService.impl;

import com.chatbots.musicInfoBot.TextFormatter;
import com.chatbots.musicInfoBot.entities.*;
import com.chatbots.musicInfoBot.entities.User;
import com.chatbots.musicInfoBot.enums.Role;
import com.chatbots.musicInfoBot.models.telegram.*;
import com.chatbots.musicInfoBot.models.telegram.buttons.InlineKeyboardButton;
import com.chatbots.musicInfoBot.models.telegram.buttons.InlineKeyboardMarkup;
import com.chatbots.musicInfoBot.models.telegram.buttons.Markup;
import com.chatbots.musicInfoBot.models.telegram.payment.LabeledPrice;
import com.chatbots.musicInfoBot.services.repositoryService.*;
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
    @Autowired
    private DictionaryRepositoryService dictionaryRepositoryService;
    @Override
    public void helpStartBotCommand(Message message) {

        if (userRepositoryService.findByChatId(message.getChat().getId()) == null) {
            User user = new User(message.getChat().getId());
            user.setUserName(message.getChat().getUserName());
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
        messageSenderService.simpleMessage("/help",callBackQuery.getMessage());

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
        messageSenderService.simpleMessage("/help",callBackQuery.getMessage());
    }

    @Override
    public void helpConcertsDataCallBack(CallBackQuery callBackQuery) {
        List<Concert>concerts = concertRepositoryService.findAll();
        String buttonText = ResourceBundle.getBundle("buttons").getString(BUY_TICKET_BUTTON.name());
        Markup markup;
        for(Concert concert:concerts){
            String caption = concert.getCity()+", "+concert.getCountry()+"\n"+concert.getLocation()+"\n"+concert.getDateTime();
            markup = new InlineKeyboardMarkup(Arrays.asList(Arrays.asList(new InlineKeyboardButton(buttonText,BUY_TICKETS_DATA.name()+"?"+concert.getId()))));
            messageSenderService.sendPhoto(concert.getPhoto(),caption,markup,callBackQuery.getMessage());
        }
        messageSenderService.simpleMessage("/help",callBackQuery.getMessage());
    }

    @Override
    public void helpBuyTicketsCallBack(CallBackQuery callBackQuery) {
        long id = Long.parseLong(TextFormatter.ejectSingleVariable(callBackQuery.getData()));
        Concert concert = concertRepositoryService.getOne(id);

        TelegramRequest telegramRequest = new TelegramRequest();
        telegramRequest.setCurrency("USD");
        telegramRequest.setTitle("Music info bot");
        telegramRequest.setChatId(callBackQuery.getMessage().getChat().getId());
        telegramRequest.setDescription("Ticket in "+concert.getCity());
        telegramRequest.setPayload(concert.getId().toString());
        telegramRequest.setPrices(Arrays.asList(new LabeledPrice("labe",concert.getPrice())));
        telegramRequest.setStartParameter("pay");
        telegramRequest.setProviderToken("284685063:TEST:MWNkMDEwOGY3ZGM3");
        messageSenderService.sendInvoice(callBackQuery.getMessage(),telegramRequest);
    }

    @Override
    public void helpPreCheckoutQueryHandle(PreCheckoutQuery preCheckoutQuery) {
    messageSenderService.sendPreCheckoutQuery(preCheckoutQuery);
    }

    @Override
    public void successfulPayment(Message message) {
        Long payload = Long.parseLong(message.getSuccessfulPayment().getInvoicePayload());
        Concert concert = concertRepositoryService.getOne(payload);
        messageSenderService.sendPhoto("https://cdnqrcgde.s3-eu-west-1.amazonaws.com/wp-content/uploads/2013/11/jpeg.jpg","Your ticket. Please don`t send it to anybody.",null,message);
        messageSenderService.simpleMessage("/help",message);
    }

    @Override
    public void helpMessageCommand(Message message) {
        String[] strings = message.getText().split("&");
        if (strings.length == 2) {
            if (strings[0].equals("setpermission"))
                setRole(message, strings);
            else
                wrongPerm(message);
        } else if (strings.length == 3) {
            if (strings[0].equals("sethellomessage"))
                setHelloMessage(message, strings);
            else
                wrongHelloMsg(message);
        } else
            messageSenderService.errorMessage(message);
    }

    private void wrongHelloMsg(Message message) {
    }

    private void setHelloMessage(Message message, String[] strings) {
        String image = strings[1];
        String text = strings[2];
        Dictionary dictionary = dictionaryRepositoryService.findById(HELLO_MESSAGE.name());
        dictionary.setValue(text);
        dictionary.setPhotoUrl(image);
        dictionaryRepositoryService.saveAndFlush(dictionary);
        messageSenderService.simpleMessage(ResourceBundle.getBundle("dictionary").getString(DONE.name()),message);
    }


    private void wrongPerm(Message message){
        String text = ResourceBundle.getBundle("dictionary").getString(SOMETHING_WRONG_ROLE.name());
        messageSenderService.simpleMessage(text,message);
    }

    private void setRole(Message message, String[] strings) {
        String userName = strings[1] ;

        if(userRepositoryService.findByUserName(userName)!=null) {
            setRoleFinal(userName,message);
        }
        else {
            String text = ResourceBundle.getBundle("dictionary").getString(USER_NAME_NOT_EXISTS.name());
            messageSenderService.simpleMessage(text,message);
        }
    }

    private void setRoleFinal(String userName, Message message) {
        User user = userRepositoryService.findByUserName(userName);
        if(user.getRole()==null || user.getRole() == Role.CUSTOMER)
        user.setRole(Role.MODERATOR);
        else user.setRole(Role.CUSTOMER);
        userRepositoryService.saveAndFlush(user);
        String done = ResourceBundle.getBundle("dictionary").getString(DONE.name());
        messageSenderService.simpleMessage(done+" /help",message);
        String userNameSet = String.format(ResourceBundle.getBundle("dictionary").getString(ROLE_SET.name()),user.getRole().name());
        message.getChat().setId(user.getChatId());
        messageSenderService.simpleMessage(userNameSet+" /help",message);
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
