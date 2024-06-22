package com.openclassrooms.chatpo.services.impl;

import com.openclassrooms.chatpo.models.User;
import com.openclassrooms.chatpo.repositories.UserRepository;
import com.openclassrooms.chatpo.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User save(User user) {
        Optional<User> findUser = userRepository.findByEmail(user.getEmail());

        if (findUser.isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUpdatedAt(LocalDate.now());

        return userRepository.save(user);

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
