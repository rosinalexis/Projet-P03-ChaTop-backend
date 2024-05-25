package com.openclassrooms.chatpo.services.impl;

import com.openclassrooms.chatpo.dto.MessageDto;
import com.openclassrooms.chatpo.models.Message;
import com.openclassrooms.chatpo.repositories.MessageRepository;
import com.openclassrooms.chatpo.services.MessageService;
import com.openclassrooms.chatpo.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ObjectsValidator<MessageDto> validator;

    @Override
    public Integer save(MessageDto dto) {
        validator.validate(dto);
        Message message = MessageDto.toEntity(dto);

        return messageRepository.save(message).getId();
    }

    @Override
    public List<MessageDto> findAll() {
        List<Message> messages = messageRepository.findAll();
        return messages.stream()
                .map(MessageDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public MessageDto findById(Integer id) {
        return messageRepository.findById(id)
                .map(MessageDto::fromEntity)
                .orElseThrow(
                        () -> new EntityNotFoundException("Message with id " + id + " not found")
                );
    }

    @Override
    public void delete(Integer id) {
        messageRepository.deleteById(id);
    }
}
