package com.chatbots.musicInfoBot.repositories;

import com.chatbots.musicInfoBot.entities.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoIdRepository extends JpaRepository<Photo,Long> {
    public Photo findTopByOrderByIdDesc();
}
