package com.openclassrooms.chatpo.controllers;

import com.openclassrooms.chatpo.dto.MessageResponseDto;
import com.openclassrooms.chatpo.dto.RentalDto;
import com.openclassrooms.chatpo.models.Rental;
import com.openclassrooms.chatpo.models.User;
import com.openclassrooms.chatpo.services.RentalService;
import com.openclassrooms.chatpo.services.UserService;
import com.openclassrooms.chatpo.validators.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;
    private final ObjectsValidator<RentalDto> validator;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<MessageResponseDto> save(
            @ModelAttribute RentalDto rentalDto,
            Authentication authUser
    ) {
        MessageResponseDto messageResponseDto = new MessageResponseDto();

        User user = userService.findByEmail(authUser.getName());
        int ownerId = user.getId();

        rentalDto.setOwnerId(ownerId);

        validator.validate(rentalDto);

        Rental rental = RentalDto.toEntity(rentalDto);

        if (rentalService.save(rental) > 0) {
            messageResponseDto.setMessage("Rental created !");
        }

        return new ResponseEntity<>(messageResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RentalDto>> findAll() {

        List<Rental> rentailList = rentalService.findAll();

        List<RentalDto> rentalDtoList = new ArrayList<>();

        for (Rental rental : rentailList) {
            rentalDtoList.add(RentalDto.fromEntity(rental));
        }

        return new ResponseEntity<>(rentalDtoList, HttpStatus.OK);
    }

    @GetMapping("/{rentalId}")
    public ResponseEntity<RentalDto> findById(
            @PathVariable("rentalId") Integer rentalId) {

        Rental rental = rentalService.findById(rentalId);

        return ResponseEntity.ok(RentalDto.fromEntity(rental));
    }


    @PutMapping(name = "/{rentalId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponseDto> updateById(
            //@RequestPart("file") MultipartFile file,
            @PathVariable("rentalId") Integer rentalId,
            @RequestBody RentalDto rentalDto,
            Authentication authUser
    ) {

        MessageResponseDto messageResponseDto = new MessageResponseDto();

        User user = userService.findByEmail(authUser.getName());
        int ownerId = user.getId();
        rentalDto.setOwnerId(ownerId);

        validator.validate(rentalDto);

        Rental rental = RentalDto.toEntity(rentalDto);

        if (rentalService.update(rentalId, rental) > 0) {
            messageResponseDto.setMessage("Rental updated !");
        }

        return new ResponseEntity<>(messageResponseDto, HttpStatus.OK);
    }


}
