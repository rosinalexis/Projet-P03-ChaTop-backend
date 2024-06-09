package com.openclassrooms.chatpo.services;

import com.openclassrooms.chatpo.models.User;

public interface UserService {
    Integer save(User user);

    User findById(Integer id);

    User findByEmail(String email);
    
}
