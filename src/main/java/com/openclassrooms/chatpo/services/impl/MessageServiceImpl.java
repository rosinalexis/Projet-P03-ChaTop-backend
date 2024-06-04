package com.openclassrooms.chatpo.services.impl;

import com.openclassrooms.chatpo.models.Message;
import com.openclassrooms.chatpo.repositories.MessageRepository;
import com.openclassrooms.chatpo.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    
    @Override
    public Integer save(Message message) {
        return messageRepository.save(message).getId();
    }

}
