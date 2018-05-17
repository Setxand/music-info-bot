package com.chatbots.musicInfoBot;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
@Component
public class ServerStarting {
    private static final Logger logger =  Logger.getLogger(ServerStarting.class);
    @Value("${url.telegram}")
    private String TELEGRAM_URL;
    @Value("${url.server}")
    private String SERVER_URL;

    @PostConstruct
    public void startServer(){
        try {
            ResponseEntity<?> responseEntity = new RestTemplate().getForEntity(TELEGRAM_URL+"/setWebhook?url="+SERVER_URL+"/telegramWebHook",Object.class);
            logger.debug("Webhook: "+responseEntity.getBody().toString());
        }
        catch (HttpClientErrorException ex){
            logger.warn(ex);
        }
    }
}
