package com.openclassrooms.chatpo.repositories;

import com.openclassrooms.chatpo.models.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Integer> {
}
