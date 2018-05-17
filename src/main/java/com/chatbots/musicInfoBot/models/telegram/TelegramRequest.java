package com.chatbots.musicInfoBot.models.telegram;

    import com.chatbots.musicInfoBot.models.telegram.buttons.Markup;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TelegramRequest {
    private String url;
    private String text;
    @JsonProperty("chat_id")
    private Integer chatId;
    @JsonProperty("reply_markup")
    private Markup markup;
    private String photo;
    private String caption;
    private String video;

    public TelegramRequest(String text, Integer chatId) {
        this.text = text;
        this.chatId = chatId;
    }

    public TelegramRequest(Integer chatId, Markup markup, String photo, String caption) {
        this.chatId = chatId;
        this.markup = markup;
        this.photo = photo;
        this.caption = caption;
    }

    public TelegramRequest(Integer chatId, String video) {
        this.chatId = chatId;
        this.video = video;
    }
}
