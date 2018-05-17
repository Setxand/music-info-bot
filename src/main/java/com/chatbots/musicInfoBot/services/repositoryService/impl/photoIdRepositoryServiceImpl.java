package com.chatbots.musicInfoBot.services.repositoryService.impl;

import com.chatbots.musicInfoBot.entities.PhotoId;
import com.chatbots.musicInfoBot.repositories.PhotoIdRepository;
import com.chatbots.musicInfoBot.services.repositoryService.PhotoIdRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class photoIdRepositoryServiceImpl implements PhotoIdRepositoryService {
    @Autowired
    private PhotoIdRepository photoIdRepository;
    @Override
    public PhotoId findTop() {
        return photoIdRepository.findTopByOrderByIdDesc();
    }

    @Override
    public PhotoId saveAndFlush(PhotoId photoId) {
        return photoIdRepository.saveAndFlush(photoId);
    }

    @Override
    public void delete(PhotoId photoId) {
        photoIdRepository.delete(photoId);
    }
}
