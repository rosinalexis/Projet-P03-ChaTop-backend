package com.openclassrooms.chatpo.controllers;

import com.openclassrooms.chatpo.dto.MessageResponseDto;
import com.openclassrooms.chatpo.dto.RentalDto;
import com.openclassrooms.chatpo.dto.RentalResponseDto;
import com.openclassrooms.chatpo.models.Rental;
import com.openclassrooms.chatpo.models.User;
import com.openclassrooms.chatpo.services.FileStorageService;
import com.openclassrooms.chatpo.services.RentalService;
import com.openclassrooms.chatpo.services.UserService;
import com.openclassrooms.chatpo.validators.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {

    private static final Logger log = LoggerFactory.getLogger(RentalController.class);
    private final ObjectsValidator<RentalDto> validator;
    private final RentalService rentalService;
    private final UserService userService;
    private final FileStorageService fileStorageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponseDto> save(
            @ModelAttribute RentalDto rentalDto,
            @RequestPart("picture") MultipartFile file,
            Authentication authUser
    ) throws IOException {
        MessageResponseDto messageResponseDto = new MessageResponseDto();

        if (file.isEmpty()) {
            messageResponseDto.setMessage("rental file is empty ! [OK]");
        }

        String filePath = fileStorageService.saveFile(file);
        rentalDto.setPictureUrl(filePath);

        User user = userService.findByEmail(authUser.getName());
        rentalDto.setOwnerId(user.getId());

        validator.validate(rentalDto);

        Rental rental = RentalDto.toEntity(rentalDto);

        if (rentalService.save(rental) > 0) {
            messageResponseDto.setMessage("Rental created !");
            log.info("rental saved successfully ! [OK]");
        }

        return new ResponseEntity<>(messageResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<RentalResponseDto> findAll() {

        List<Rental> rentailList = rentalService.findAll();

        RentalResponseDto rentalDtoList = new RentalResponseDto(rentailList);
        log.info("rentals found ! [OK]");

        return new ResponseEntity<>(rentalDtoList, HttpStatus.OK);
    }

    @GetMapping("/{rentalId}")
    public ResponseEntity<RentalDto> findById(
            @PathVariable("rentalId") Integer rentalId) {

        Rental rental = rentalService.findById(rentalId);
        log.info("rental found ! [OK]");

        return ResponseEntity.ok(RentalDto.fromEntity(rental));
    }


    @PutMapping(path = "/{rentalId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponseDto> updateById(
            @PathVariable("rentalId") Integer rentalId,
            @ModelAttribute RentalDto rentalDto,
            Authentication authUser
    ) {

        MessageResponseDto messageResponseDto = new MessageResponseDto();

        User user = userService.findByEmail(authUser.getName());
        rentalDto.setOwnerId(user.getId());

        validator.validate(rentalDto);

        Rental rental = RentalDto.toEntity(rentalDto);

        if (rentalService.update(rentalId, rental) > 0) {
            messageResponseDto.setMessage("Rental updated !");
            log.info("rental updated successfully ! [OK]");
        }

        return new ResponseEntity<>(messageResponseDto, HttpStatus.OK);
    }

}
