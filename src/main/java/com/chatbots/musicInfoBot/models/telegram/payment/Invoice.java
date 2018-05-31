package com.chatbots.musicInfoBot.models.telegram.payment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Invoice {
    private String title;
    private String description;
    @JsonProperty("start_parameter")
    private String startParameter;
    private String currency;
    @JsonProperty("total_amount")
    private String totalAmount;
}
