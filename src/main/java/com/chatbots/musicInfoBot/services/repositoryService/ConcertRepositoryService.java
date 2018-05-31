package com.chatbots.musicInfoBot.services.repositoryService;

import com.chatbots.musicInfoBot.entities.Concert;

import java.util.List;

public interface ConcertRepositoryService {
    public List<Concert> findAll();
    public Concert getOne(Long id);
}
