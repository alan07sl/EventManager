package com.utn.tacs.eventmanager.config;

import com.utn.tacs.eventmanager.services.TelegramIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

@Component
public class PostConstructExampleBean {

    @Value("${springBootApp.postConstructOff:false}")
    private boolean postConstructOff = false;

    @Autowired
    private TelegramIntegrationService tis;
 
    @PostConstruct
    public void init() {
        if(!postConstructOff) {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
            try {
                telegramBotsApi.registerBot(tis);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}