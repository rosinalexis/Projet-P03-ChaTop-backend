package com.openclassrooms.chatpo.controllers;

import com.openclassrooms.chatpo.dto.MessageDto;
import com.openclassrooms.chatpo.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<Integer> save(
            @RequestBody MessageDto messageDto
    ) {
        return ResponseEntity.ok(messageService.save(messageDto));
    }
}
