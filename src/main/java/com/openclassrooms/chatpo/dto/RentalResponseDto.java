package com.openclassrooms.chatpo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RentalResponseDto {
    private List<RentalDto> rentals = new ArrayList<>();
}
