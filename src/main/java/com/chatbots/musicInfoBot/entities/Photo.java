package com.chatbots.musicInfoBot.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Photo {
    @Id
    @GeneratedValue
    private Long id;
    private String fileId;

    public Photo(String fileId) {
        this.fileId = fileId;
    }
}
