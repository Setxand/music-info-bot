package com.chatbots.musicInfoBot.repositories;

import com.chatbots.musicInfoBot.entities.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertRepository extends JpaRepository<Concert,Long>{

}
