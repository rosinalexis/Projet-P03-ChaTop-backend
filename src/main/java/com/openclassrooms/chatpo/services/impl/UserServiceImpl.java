package com.openclassrooms.chatpo.services.impl;

import com.openclassrooms.chatpo.models.User;
import com.openclassrooms.chatpo.repositories.UserRepository;
import com.openclassrooms.chatpo.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public Integer save(User user) {
        return userRepository.save(user).getId();
    }


    @Override
    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("User with id " + id + " not found")
                );
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("User with email " + email + " not found")
        );
    }

}
