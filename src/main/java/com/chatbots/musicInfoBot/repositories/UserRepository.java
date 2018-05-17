package com.chatbots.musicInfoBot.repositories;

import com.chatbots.musicInfoBot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long>{
    public User findByChatId(Integer chatId);
}
