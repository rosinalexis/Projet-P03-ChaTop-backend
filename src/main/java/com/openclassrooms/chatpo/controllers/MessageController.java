package com.openclassrooms.chatpo.controllers;

import com.openclassrooms.chatpo.dto.MessageDto;
import com.openclassrooms.chatpo.dto.MessageResponseDto;
import com.openclassrooms.chatpo.models.Message;
import com.openclassrooms.chatpo.services.MessageService;
import com.openclassrooms.chatpo.validators.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private static final Logger log = LoggerFactory.getLogger(MessageController.class);
    private final MessageService messageService;
    private final ObjectsValidator<MessageDto> validator;

    @PostMapping
    public ResponseEntity<MessageResponseDto> save(
            @RequestBody MessageDto messageDto

    ) {
        log.debug("messageDto: {}", messageDto);
        validator.validate(messageDto);
        MessageResponseDto messageResponseDto = new MessageResponseDto();
        Message message = MessageDto.toEntity(messageDto);

        if (messageService.save(message) > 0) {
            messageResponseDto.setMessage("Message send with success");
        }
        return new ResponseEntity<>(messageResponseDto, HttpStatus.OK);
    }
}
