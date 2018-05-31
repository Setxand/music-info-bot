package com.chatbots.musicInfoBot.services.repositoryService;

import com.chatbots.musicInfoBot.entities.Gallery;

import java.util.List;

public interface GalleryRepositoryService {
    public List<Gallery>findListOfGallery(String type);
}
