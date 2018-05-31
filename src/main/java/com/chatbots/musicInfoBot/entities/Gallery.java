package com.chatbots.musicInfoBot.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Gallery {
    @Id
    @GeneratedValue
    private Long id;
    private String type;
    private String media;
    private String caption;
}
