package com.openclassrooms.chatpo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LoginRequestDto {


    @NotNull
    @NotBlank
    @NotEmpty
    @Email
    private String login;

    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 6, max = 16)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

}