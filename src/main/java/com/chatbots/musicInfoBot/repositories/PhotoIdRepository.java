package com.chatbots.musicInfoBot.repositories;

import com.chatbots.musicInfoBot.entities.PhotoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoIdRepository extends JpaRepository<PhotoId,Long> {
    public PhotoId findTopByOrderByIdDesc();
}
