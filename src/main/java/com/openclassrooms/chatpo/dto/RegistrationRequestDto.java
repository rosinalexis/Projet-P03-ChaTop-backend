package com.openclassrooms.chatpo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationRequestDto {
    
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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;


}
