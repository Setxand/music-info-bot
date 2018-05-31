package com.chatbots.musicInfoBot.services.repositoryService.impl;

import com.chatbots.musicInfoBot.entities.Dictionary;
import com.chatbots.musicInfoBot.repositories.DictionaryRepository;
import com.chatbots.musicInfoBot.services.repositoryService.DictionaryRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DictionaryRepositoryServiceImpl implements DictionaryRepositoryService {
    @Autowired
    private DictionaryRepository dictionaryRepository;
    @Override
    public Dictionary findById(String id) {
        return dictionaryRepository.getOne(id);
    }

    @Override
    public Dictionary saveAndFlush(Dictionary dictionary) {
        return dictionaryRepository.saveAndFlush(dictionary);
    }
}
