package com.chatbots.musicInfoBot.services.repositoryService;

import com.chatbots.musicInfoBot.entities.PhotoId;

public interface PhotoIdRepositoryService {
    public PhotoId findTop();
    public PhotoId saveAndFlush(PhotoId photoId);
    public void delete(PhotoId photoId);
}
