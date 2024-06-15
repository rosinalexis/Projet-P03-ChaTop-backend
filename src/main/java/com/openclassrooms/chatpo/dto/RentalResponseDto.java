package com.openclassrooms.chatpo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.openclassrooms.chatpo.models.Rental;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RentalResponseDto {

    @JsonProperty("rentals")
    private List<RentalDto> rentalDtoList;

    public RentalResponseDto(List<Rental> rentalList) {

        List<RentalDto> rentalDtoList = new ArrayList<>();

        for (Rental rental : rentalList) {
            rentalDtoList.add(RentalDto.fromEntity(rental));
        }
        this.rentalDtoList = rentalDtoList;
    }

}
