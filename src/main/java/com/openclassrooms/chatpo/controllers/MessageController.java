package com.openclassrooms.chatpo.controllers;

import com.openclassrooms.chatpo.dto.MessageDto;
import com.openclassrooms.chatpo.dto.MessageResponseDto;
import com.openclassrooms.chatpo.models.Message;
import com.openclassrooms.chatpo.services.MessageService;
import com.openclassrooms.chatpo.validators.ObjectsValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@SecurityRequirement(
        name = "bearerAuth"
)
@Tag(name = "Message")
public class MessageController {

    private static final Logger log = LoggerFactory.getLogger(MessageController.class);
    private final ObjectsValidator<MessageDto> validator;
    private final MessageService messageService;

    @Operation(
            description = "Post endpoint to save a new message. This endpoint validates the provided message data, saves the message to the database, and returns a success message.",
            summary = "Save a new message",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Message saved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageResponseDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized / Invalid Token",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MessageResponseDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400",
                            description = "Invalid message data",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageResponseDto.class)
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<MessageResponseDto> save(
            @RequestBody MessageDto messageDto
    ) {
        validator.validate(messageDto);

        MessageResponseDto messageResponseDto = new MessageResponseDto();
        Message message = MessageDto.toEntity(messageDto);


        if (messageService.save(message) > 0) {
            messageResponseDto.setMessage("Message send with success");
            log.info("Message creation status [OK]");
        }
        return new ResponseEntity<>(messageResponseDto, HttpStatus.OK);
    }
}
