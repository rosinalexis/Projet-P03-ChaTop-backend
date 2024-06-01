package com.openclassrooms.chatpo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.openclassrooms.chatpo.models.Rental;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class RentalDto {

    private Integer id;

    @NotNull
    @NotBlank
    @NotEmpty
    private String name;

    @PositiveOrZero
    private BigDecimal surface;

    @PositiveOrZero
    private BigDecimal price;

    //TODO conversion en fileSysteme et recuperation de l'url
    private String picture;

    private String description;

    @JsonProperty("owner_id")
    private Integer ownerId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static RentalDto fromEntity(Rental rental) {

        return RentalDto.builder()
                .id(rental.getId())
                .name(rental.getName())
                .surface(rental.getSurface())
                .price(rental.getPrice())
                .picture(rental.getPicture())
                .description(rental.getDescription())
                .ownerId(rental.getOwner().getId())
                .build();
    }

    public static Rental toEntity(RentalDto rental) {

        return Rental.builder()
                .id(rental.getId())
                .name(rental.getName())
                .surface(rental.getSurface())
                .price(rental.getPrice())
                .picture(rental.getPicture())
                .description(rental.getDescription())
                .ownerId(rental.getOwnerId())
                .build();
    }

}
