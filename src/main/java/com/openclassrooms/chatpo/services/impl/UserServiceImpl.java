package com.openclassrooms.chatpo.services.impl;

import com.openclassrooms.chatpo.dto.UserDto;
import com.openclassrooms.chatpo.models.User;
import com.openclassrooms.chatpo.repositories.UserRepository;
import com.openclassrooms.chatpo.services.UserService;
import com.openclassrooms.chatpo.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ObjectsValidator<UserDto> validator;

    @Override
    public Integer save(UserDto dto) {
        validator.validate(dto);
        User user = UserDto.toEntity(dto);
        return userRepository.save(user).getId();
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(UserDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(Integer id) {
        return userRepository.findById(id)
                .map(UserDto::fromEntity)
                .orElseThrow(
                        () -> new EntityNotFoundException("User with id " + id + " not found")
                );
    }

    @Override
    public void delete(Integer id) {

        userRepository.deleteById(id);

    }
}
