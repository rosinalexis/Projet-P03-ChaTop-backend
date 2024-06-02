package com.openclassrooms.chatpo.controllers;

import com.openclassrooms.chatpo.dto.LoginRequest;
import com.openclassrooms.chatpo.dto.UserDto;
import com.openclassrooms.chatpo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> save(
            @RequestBody UserDto userDto) {

        Map<String, String> response = new HashMap<>();

        if (userService.save(userDto) > 0) {
            response.put("token", "jwt");
        }

        //Todo : création du jwt via dans un package independent
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @RequestBody LoginRequest loginRequest
    ) {

        Map<String, String> response = new HashMap<>();

        //Todo : création du jwt via dans un package independent
        log.debug("Login request: {} {}", loginRequest.getLogin(), loginRequest.getPassword());
        response.put("token", "jwt");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() {

        //Todo : récuperation de l'utilisateur via jwt token
        return ResponseEntity.ok(userService.findById(1));
    }
}
