package com.chatbots.musicInfoBot.entities;

import com.chatbots.musicInfoBot.enums.Role;
import com.chatbots.musicInfoBot.enums.Status;
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
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private Integer chatId;
    private Role role;
    private Status status;

    public User(Integer chatId) {
        this.chatId = chatId;
    }
}
