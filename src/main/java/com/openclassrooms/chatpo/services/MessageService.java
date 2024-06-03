package com.openclassrooms.chatpo.services;

import com.openclassrooms.chatpo.models.Message;

public interface MessageService {
    Integer save(Message message);
}
