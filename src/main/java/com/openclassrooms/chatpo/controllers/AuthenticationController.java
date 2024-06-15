package com.openclassrooms.chatpo.controllers;

import com.openclassrooms.chatpo.dto.LoginRequestDto;
import com.openclassrooms.chatpo.dto.RegistrationRequestDto;
import com.openclassrooms.chatpo.dto.TokenDto;
import com.openclassrooms.chatpo.dto.UserDto;
import com.openclassrooms.chatpo.models.Token;
import com.openclassrooms.chatpo.models.User;
import com.openclassrooms.chatpo.services.UserService;
import com.openclassrooms.chatpo.services.auth.AuthenticationService;
import com.openclassrooms.chatpo.services.auth.JwtService;
import com.openclassrooms.chatpo.validators.ObjectsValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    private final UserService userService;
    private final ObjectsValidator<RegistrationRequestDto> validator;
    private final ObjectsValidator<LoginRequestDto> loginValidator;
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TokenDto> register(
            @RequestBody @Valid RegistrationRequestDto request) {

        validator.validate(request);

        Token token = authenticationService.register(request);

        log.info("User registration [OK]");

        return new ResponseEntity<>(TokenDto.fromEntity(token), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(
            @RequestBody @Valid LoginRequestDto loginRequest
    ) {

        loginValidator.validate(loginRequest);

        Token token = authenticationService.authenticate(loginRequest);

        log.info("User login  with token [OK]");

        return new ResponseEntity<>(TokenDto.fromEntity(token), HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(
            Authentication authUser
    ) {
        User user = userService.findByEmail(authUser.getName());

        log.info("User identification [OK]");

        return ResponseEntity.ok(UserDto.fromEntity(user));
    }
}
