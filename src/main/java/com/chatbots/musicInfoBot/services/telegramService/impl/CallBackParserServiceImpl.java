package com.chatbots.musicInfoBot.services.telegramService.impl;


import com.chatbots.musicInfoBot.enums.CallBackData;
import com.chatbots.musicInfoBot.models.telegram.CallBackQuery;
import com.chatbots.musicInfoBot.services.telegramService.CallBackParserService;
import com.chatbots.musicInfoBot.services.telegramService.HelperService;
import com.chatbots.musicInfoBot.services.telegramService.TelegramMessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;


@Service
public class CallBackParserServiceImpl implements CallBackParserService {
    @Autowired
    private HelperService helperService;
    @Autowired
    private TelegramMessageSenderService telegramMessageSenderService;
    @Override
    public void parseCallBackQuery(CallBackQuery callBackQuery) {
        switch (CallBackData.valueOf(callBackQuery.getData())){
            case VIDEO_DATA:
                videoData(callBackQuery);
                break;
            case NEW_NEWS_DATA:
                newNewsData(callBackQuery);
                break;
                default:
                    telegramMessageSenderService.errorMessage(callBackQuery.getMessage());
                    break;
        }
    }

    private void newNewsData(CallBackQuery callBackQuery) {
        helperService.helpNewNewsDataCallBack(callBackQuery);
    }

    private void videoData(CallBackQuery callBackQuery) {
        helperService.helpVideoDataCallBack(callBackQuery);
    }
}
