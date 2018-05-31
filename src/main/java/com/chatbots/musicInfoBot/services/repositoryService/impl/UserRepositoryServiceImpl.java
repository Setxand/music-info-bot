package com.chatbots.musicInfoBot.services.repositoryService.impl;

import com.chatbots.musicInfoBot.entities.User;
import com.chatbots.musicInfoBot.enums.Status;
import com.chatbots.musicInfoBot.repositories.UserRepository;
import com.chatbots.musicInfoBot.services.repositoryService.UserRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRepositoryServiceImpl implements UserRepositoryService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByChatId(Integer chatId) {
        return userRepository.findByChatId(chatId);
    }

    @Override
    public User saveAndFlush(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public void changeStatus(User user, Status status) {
        user.setStatus(status);
        saveAndFlush(user);
    }
}
