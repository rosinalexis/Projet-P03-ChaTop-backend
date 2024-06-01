package com.openclassrooms.chatpo.controllers;

import com.openclassrooms.chatpo.dto.RentalDto;
import com.openclassrooms.chatpo.services.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @PostMapping
    public ResponseEntity<Map<String, String>> save(
            @ModelAttribute RentalDto rentalDto
    ) {

        Map<String, String> response = new HashMap<>();

        if (rentalService.save(rentalDto) > 0) {
            response.put("message", "Rental created !");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Map<String, List<RentalDto>>> findAll() {

        Map<String, List<RentalDto>> response = new HashMap<>();

        List<RentalDto> rentailList = rentalService.findAll();

        response.put("rentals", rentailList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{rental-id}")
    public ResponseEntity<RentalDto> findById(
            @PathVariable("rental-id") Integer rentalId) {

        return ResponseEntity.ok(rentalService.findById(rentalId));
    }


    @PutMapping("/{rental-id}")
    public ResponseEntity<Map<String, String>> updateById(
            @PathVariable("rental-id") Integer rentalId,
            @RequestBody RentalDto rentalDto
    ) {

        Map<String, String> response = new HashMap<>();

        if (rentalService.updateById(rentalId, rentalDto) > 0) {
            response.put("message", "Rental updated !");
        }
        
        return ResponseEntity.ok(response);
    }


}
