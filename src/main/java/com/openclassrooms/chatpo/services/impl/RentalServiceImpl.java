package com.openclassrooms.chatpo.services.impl;

import com.openclassrooms.chatpo.models.Rental;
import com.openclassrooms.chatpo.repositories.RentalRepository;
import com.openclassrooms.chatpo.services.RentalService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;


    public Integer save(Rental rental) {

        return rentalRepository.save(rental).getId();
    }

    @Override
    public List<Rental> findAll() {

        return rentalRepository.findAll();
    }

    @Override
    public Rental findById(Integer id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rental with id " + id + " not found"));
    }

    @Override
    public Integer update(Integer id, Rental rental
    ) {

        return rentalRepository.findById(id).map(
                oldRental -> {
                    oldRental.setName(rental.getName());
                    oldRental.setSurface(rental.getSurface());
                    oldRental.setPrice(rental.getPrice());
                    oldRental.setDescription(rental.getDescription());
                    return rentalRepository.save(oldRental).getId();
                }).orElseThrow(() -> new EntityNotFoundException("Rental with id " + id + " not found"));
    }

}
