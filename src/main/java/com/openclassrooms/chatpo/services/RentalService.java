package com.openclassrooms.chatpo.services;

import com.openclassrooms.chatpo.models.Rental;

import java.util.List;

public interface RentalService {

    Integer save(Rental rental);

    List<Rental> findAll();

    Rental findById(Integer id);

    Integer update(Integer id, Rental rental);
}
