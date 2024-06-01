package com.openclassrooms.chatpo.services.impl;

import com.openclassrooms.chatpo.dto.RentalDto;
import com.openclassrooms.chatpo.models.Rental;
import com.openclassrooms.chatpo.repositories.RentalRepository;
import com.openclassrooms.chatpo.services.RentalService;
import com.openclassrooms.chatpo.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final ObjectsValidator<RentalDto> validator;


    @Override
    public Integer save(RentalDto dto) {
        validator.validate(dto);

        Rental rental = RentalDto.toEntity(dto);
        return rentalRepository.save(rental).getId();
    }

    @Override
    public List<RentalDto> findAll() {

        List<Rental> rentals = rentalRepository.findAll();

        return rentals.stream()
                .map(RentalDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public RentalDto findById(Integer id) {
        return rentalRepository.findById(id)
                .map(RentalDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Rental with id " + id + " not found"));
    }

    @Override
    public Integer updateById(Integer id, RentalDto dto) {
        validator.validate(dto);
        Rental rental = RentalDto.toEntity(dto);
        rental.setId(id);
        return rentalRepository.save(rental).getId();
    }


    @Override
    public void delete(Integer id) {

        rentalRepository.deleteById(id);
    }


}
