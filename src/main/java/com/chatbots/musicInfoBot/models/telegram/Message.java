package com.chatbots.musicInfoBot.models.telegram;

import com.chatbots.musicInfoBot.models.telegram.payment.SuccessfulPayment;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Message {
    @JsonProperty("message_id")
    private Integer messageId;
    private User from;
    private Integer date;
    private Chat chat;
    private String text;
    private List<TelegramEntity>entities;
    private List<PhotoSize>photo;
    @JsonProperty("successful_payment")
    private SuccessfulPayment successfulPayment;

}
