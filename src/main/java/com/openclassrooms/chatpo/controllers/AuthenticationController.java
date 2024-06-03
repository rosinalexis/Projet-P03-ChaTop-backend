package com.openclassrooms.chatpo.controllers;

import com.openclassrooms.chatpo.dto.LoginRequest;
import com.openclassrooms.chatpo.dto.TokenDto;
import com.openclassrooms.chatpo.dto.UserDto;
import com.openclassrooms.chatpo.models.User;
import com.openclassrooms.chatpo.services.UserService;
import com.openclassrooms.chatpo.validators.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    private final UserService userService;
    private final ObjectsValidator<UserDto> validator;

    @PostMapping("/register")
    public ResponseEntity<TokenDto> save(
            @RequestBody UserDto userDto) {

        TokenDto tokenDto = new TokenDto();
        validator.validate(userDto);
        //Todo utilisation du passwordEncodeur pour le mot de passe
        User user = UserDto.toEntity(userDto);

        if (userService.save(user) > 0) {
            tokenDto.setToken("jwt");
        }

        //Todo : création du jwt via dans un package independent
        return new ResponseEntity<>(tokenDto, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(
            @RequestBody LoginRequest loginRequest
    ) {

        TokenDto tokenDto = new TokenDto();

        //Todo : création du jwt via dans un package independent
        log.debug("Login request: {} {}", loginRequest.getLogin(), loginRequest.getPassword());
        tokenDto.setToken("jwt");

        return new ResponseEntity<>(tokenDto, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() {
        User user = userService.findById(1);
        //Todo : récuperation de l'utilisateur via jwt token
        return ResponseEntity.ok(UserDto.fromEntity(user));
    }
}
