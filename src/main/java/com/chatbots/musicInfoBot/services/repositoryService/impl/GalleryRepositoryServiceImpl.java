package com.chatbots.musicInfoBot.services.repositoryService.impl;

import com.chatbots.musicInfoBot.entities.Gallery;
import com.chatbots.musicInfoBot.repositories.GalleryRepository;
import com.chatbots.musicInfoBot.services.repositoryService.GalleryRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GalleryRepositoryServiceImpl implements GalleryRepositoryService {
    @Autowired
    private GalleryRepository galleryRepository;
    @Override
    public List<Gallery> findListOfGallery(String type) {
        return galleryRepository.findAllByTypeOrderByIdDesc(type);
    }
}
