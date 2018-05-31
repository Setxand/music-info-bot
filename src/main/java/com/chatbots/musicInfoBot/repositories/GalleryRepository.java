package com.chatbots.musicInfoBot.repositories;

import com.chatbots.musicInfoBot.entities.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GalleryRepository extends JpaRepository<Gallery,Long>{
    public List<Gallery>findAllByTypeOrderByIdDesc(String type);
}
