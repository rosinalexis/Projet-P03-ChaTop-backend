package com.openclassrooms.chatpo.controllers;

import com.openclassrooms.chatpo.dto.UserDto;
import com.openclassrooms.chatpo.models.User;
import com.openclassrooms.chatpo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;


    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> findById(
            @PathVariable("userId") Integer userId
    ) {

        User user = userService.findById(userId);
        log.info("User with id {} found [OK]", user.getId());

        return ResponseEntity.ok(UserDto.fromEntity(user));
    }

}
