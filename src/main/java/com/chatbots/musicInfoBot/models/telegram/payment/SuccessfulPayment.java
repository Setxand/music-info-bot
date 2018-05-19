package com.chatbots.musicInfoBot.models.telegram.payment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SuccessfulPayment {
    @JsonProperty("invoice_payload")
    private String invoicePayload;
}
