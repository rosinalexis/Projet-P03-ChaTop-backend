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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
@SecurityRequirement(
        name = "bearerAuth"
)
@Tag(name = "Rental")
public class RentalController {

    private static final Logger log = LoggerFactory.getLogger(RentalController.class);
    private final ObjectsValidator<RentalDto> validator;
    private final RentalService rentalService;
    private final UserService userService;
    private final FileStorageService fileStorageService;

    @Operation(
            description = "Post endpoint to save a new rental. This endpoint accepts rental data including a picture file, saves the rental information to the database, and returns a success message.",
            summary = "Save a new rental",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Rental created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageResponseDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized / Invalid Token",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MessageResponseDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400",
                            description = "Invalid rental data or missing picture",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MessageResponseDto.class)
                            )
                    ),
            }
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponseDto> save(
            @ModelAttribute RentalDto rentalDto,
            Authentication authUser
    ) throws IOException {
        MessageResponseDto messageResponseDto = new MessageResponseDto();

        if (rentalDto.getPicture() == null) {
            throw new IllegalArgumentException("Picture is required");
        }

        String filePath = fileStorageService.saveFile(rentalDto.getPicture());
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

    @Operation(
            description = "Get endpoint to retrieve all rentals. This endpoint returns a list of all rental properties available.",
            summary = "Retrieve all rentals",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Rentals retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = RentalResponseDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized / Invalid Token",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MessageResponseDto.class)
                            )
                    )
            }
    )
    @GetMapping
    public ResponseEntity<RentalResponseDto> findAll() {

        List<Rental> rentailList = rentalService.findAll();

        RentalResponseDto rentalDtoList = new RentalResponseDto(rentailList);
        log.info("rentals found ! [OK]");

        return new ResponseEntity<>(rentalDtoList, HttpStatus.OK);
    }

    @Operation(
            description = "Get endpoint to find a rental by its ID. This endpoint retrieves the details of a rental property using the provided rental ID.",
            summary = "Find rental by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Rental found successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RentalDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized / Invalid Token",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MessageResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Rental not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageResponseDto.class)
                            )
                    )
            }
    )
    @GetMapping("/{rentalId}")
    public ResponseEntity<RentalDto> findById(
            @PathVariable("rentalId") Integer rentalId) {

        Rental rental = rentalService.findById(rentalId);
        log.info("rental found ! [OK]");

        return ResponseEntity.ok(RentalDto.fromEntity(rental));
    }


    @Operation(
            description = "Put endpoint to update a rental by its ID. This endpoint updates the details of a rental property using the provided rental ID and new rental data.",
            summary = "Update rental by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Rental updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = MessageResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid rental data",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Rental not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = MessageResponseDto.class)
                            )
                    )
            }
    )
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
