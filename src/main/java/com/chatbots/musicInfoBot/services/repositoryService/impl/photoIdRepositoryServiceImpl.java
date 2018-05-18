package com.chatbots.musicInfoBot.services.repositoryService.impl;

import com.chatbots.musicInfoBot.entities.Photo;
import com.chatbots.musicInfoBot.repositories.PhotoIdRepository;
import com.chatbots.musicInfoBot.services.repositoryService.PhotoIdRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class photoIdRepositoryServiceImpl implements PhotoIdRepositoryService {
    @Autowired
    private PhotoIdRepository photoIdRepository;
    @Override
    public Photo findTop() {
        return photoIdRepository.findTopByOrderByIdDesc();
    }

    @Override
    public Photo saveAndFlush(Photo photo) {
        return photoIdRepository.saveAndFlush(photo);
    }

    @Override
    public void delete(Photo photo) {
        photoIdRepository.delete(photo);
    }
}
