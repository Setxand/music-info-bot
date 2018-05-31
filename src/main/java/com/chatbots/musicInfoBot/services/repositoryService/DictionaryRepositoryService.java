package com.chatbots.musicInfoBot.services.repositoryService;

import com.chatbots.musicInfoBot.entities.Dictionary;

public interface DictionaryRepositoryService {
    Dictionary findById(String id);
    Dictionary saveAndFlush(Dictionary dictionary);
}
