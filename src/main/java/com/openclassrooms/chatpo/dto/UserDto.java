package com.openclassrooms.chatpo.dto;

import com.openclassrooms.chatpo.models.User;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserDto {

    private Integer id;

    @NotNull
    @NotBlank
    @NotEmpty
    private String name;

    @NotNull
    @NotBlank
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 6, max = 16)
    private String password;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserDto fromEntity(User user) {

        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .updatedAt(user.getUpdatedAt())
                .createdAt(user.getCreatedAt())
                .build();
    }


    public static User toEntity(UserDto user) {
        return User.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }


}
