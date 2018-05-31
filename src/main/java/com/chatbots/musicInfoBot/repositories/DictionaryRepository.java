package com.chatbots.musicInfoBot.repositories;

import com.chatbots.musicInfoBot.entities.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DictionaryRepository extends JpaRepository<Dictionary,String>{
}
