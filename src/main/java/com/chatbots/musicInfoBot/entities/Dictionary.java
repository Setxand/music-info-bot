package com.chatbots.musicInfoBot.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Dictionary {
    @Id
    private String id;
    private String value;
    private String photoUrl;
}
