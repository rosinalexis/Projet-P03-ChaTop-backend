package com.openclassrooms.chatpo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.openclassrooms.chatpo.models.Message;
import com.openclassrooms.chatpo.models.Rental;
import com.openclassrooms.chatpo.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class MessageDto {

    private Integer id;

    @NotNull
    @NotBlank
    @NotEmpty
    private String message;

    @NotNull
    @JsonProperty("user_id")
    private Integer userId;
    @NotNull
    @JsonProperty("rental_id")
    private Integer rentalId;

    public static MessageDto fromEntity(Message message) {

        return MessageDto.builder()
                .id(message.getId())
                .message(message.getMessage())
                .rentalId(message.getRental().getId())
                .userId(message.getUser().getId())
                .build();
    }


    public static Message toEntity(MessageDto message) {

        return Message.builder()
                .id(message.getId())
                .message(message.getMessage())
                .user(User.builder()
                        .id(message.getUserId())
                        .build()
                )
                .rental(Rental.builder()
                        .id(message.getRentalId())
                        .build()
                )
                .build();
    }

}
