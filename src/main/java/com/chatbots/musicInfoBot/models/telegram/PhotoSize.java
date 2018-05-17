package com.chatbots.musicInfoBot.models.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PhotoSize {
    @JsonProperty("file_id")
    private String fileId;
    private Integer width;
    private Integer height;
    @JsonProperty("file_size")
    private Integer fileSize;
}
