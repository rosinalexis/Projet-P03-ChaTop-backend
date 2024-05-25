package com.openclassrooms.chatpo.repositories;

import com.openclassrooms.chatpo.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}
