package com.openclassrooms.chatpo.controllers;

import com.openclassrooms.chatpo.dto.UserDto;
import com.openclassrooms.chatpo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> save(
            @RequestBody UserDto userDto) {

        Map<String, String> response = new HashMap<>();

        //Todo : création du jwt via dans un package independent
        response.put("token", "jwt");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @RequestParam("login") String login,
            @RequestParam("password") String password
    ) {

        Map<String, String> response = new HashMap<>();

        //Todo : création du jwt via dans un package independent
        response.put("token", "jwt");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/me/{user-id}")
    public ResponseEntity<UserDto> findById(
            @PathVariable("user-id") Integer userId
    ) {
        return ResponseEntity.ok(userService.findById(userId));
    }

}
