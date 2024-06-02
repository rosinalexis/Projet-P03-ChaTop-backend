package com.openclassrooms.chatpo.controllers;

import com.openclassrooms.chatpo.dto.MessageDto;
import com.openclassrooms.chatpo.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private static final Logger log = LoggerFactory.getLogger(MessageController.class);
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<Map<String, String>> save(
            @RequestBody MessageDto messageDto
    ) {

        Map<String, String> response = new HashMap<>();

        log.debug("messageDto: {}", messageDto);

        if (messageService.save(messageDto) > 0) {
            response.put("message", "Message send with success");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
