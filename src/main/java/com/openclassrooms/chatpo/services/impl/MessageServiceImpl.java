package com.openclassrooms.chatpo.services.impl;

import com.openclassrooms.chatpo.models.Message;
import com.openclassrooms.chatpo.models.Rental;
import com.openclassrooms.chatpo.models.User;
import com.openclassrooms.chatpo.repositories.MessageRepository;
import com.openclassrooms.chatpo.services.MessageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    public Integer save(Message message) {
        User user = message.getUser();
        Rental rental = message.getRental();

        if (user.getId() == null || rental.getId() == null) {
            throw new EntityNotFoundException("User or Rental does not exist");
        }

        return messageRepository.save(message).getId();
    }

}
