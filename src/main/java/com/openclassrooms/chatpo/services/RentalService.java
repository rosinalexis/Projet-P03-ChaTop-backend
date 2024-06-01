package com.openclassrooms.chatpo.services;

import com.openclassrooms.chatpo.dto.RentalDto;

public interface RentalService extends AbstractService<RentalDto> {
    Integer updateById(Integer userId, RentalDto rentalDto);
}
