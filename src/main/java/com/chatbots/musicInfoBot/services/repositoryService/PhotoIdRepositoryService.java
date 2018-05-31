package com.chatbots.musicInfoBot.services.repositoryService;

import com.chatbots.musicInfoBot.entities.Photo;

public interface PhotoIdRepositoryService {
    public Photo findTop();
    public Photo saveAndFlush(Photo photo);
    public void delete(Photo photo);
}
