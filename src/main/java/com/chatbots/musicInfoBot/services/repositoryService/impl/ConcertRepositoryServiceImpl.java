package com.chatbots.musicInfoBot.services.repositoryService.impl;

import com.chatbots.musicInfoBot.entities.Concert;
import com.chatbots.musicInfoBot.repositories.ConcertRepository;
import com.chatbots.musicInfoBot.services.repositoryService.ConcertRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ConcertRepositoryServiceImpl implements ConcertRepositoryService {
    @Autowired
    private ConcertRepository concertRepository;
    @Override
    public List<Concert> findAll() {
        return concertRepository.findAll();
    }
}
