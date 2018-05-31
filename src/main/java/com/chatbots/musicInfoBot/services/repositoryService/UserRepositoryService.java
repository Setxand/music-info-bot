package com.chatbots.musicInfoBot.services.repositoryService;

import com.chatbots.musicInfoBot.entities.User;
import com.chatbots.musicInfoBot.enums.Status;

import java.util.List;

public interface UserRepositoryService {
    public User findByUserName(String userName);
    public List<User>findAll();
    public User findByChatId(Integer chatId);
    public User saveAndFlush(User user);
    public void delete(User user);
    public void changeStatus(User user, Status status);
}
