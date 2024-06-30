package com.openclassrooms.chatpo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.openclassrooms.chatpo.models.Rental;
import com.openclassrooms.chatpo.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    @JsonProperty(value = "picture", access = JsonProperty.Access.READ_ONLY)
    private String pictureUrl;

    @JsonProperty(value = "picture", access = JsonProperty.Access.WRITE_ONLY)
    private MultipartFile picture;

    @NotEmpty
    @NotBlank
    @NotNull
    private String description;

    @JsonProperty("owner_id")
    private Integer ownerId;

    private LocalDate created_at;

    private LocalDate updated_at;

    public static RentalDto fromEntity(Rental rental) {

        return RentalDto.builder()
                .id(rental.getId())
                .name(rental.getName())
                .surface(rental.getSurface())
                .price(rental.getPrice())
                .pictureUrl(rental.getPicture())
                .description(rental.getDescription())
                .ownerId(rental.getOwner().getId())
                .updated_at(rental.getUpdatedAt())
                .created_at(rental.getCreatedAt())
                .build();
    }

    public static Rental toEntity(RentalDto rental) {

        return Rental.builder()
                .id(rental.getId())
                .name(rental.getName())
                .surface(rental.getSurface())
                .price(rental.getPrice())
                .picture(rental.getPictureUrl())
                .description(rental.getDescription())
                .owner(User.builder()
                        .id(rental.getOwnerId())
                        .build())
                .build();
    }

}
