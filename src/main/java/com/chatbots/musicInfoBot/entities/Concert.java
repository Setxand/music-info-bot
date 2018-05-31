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
public class Concert {
    @Id
    @GeneratedValue
    private Long id;
    private String photo;
    private String country;
    private String city;
    private String location;
    private String dateTime;
    private Integer price;

}
