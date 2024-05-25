package com.openclassrooms.chatpo.repositories;

import com.openclassrooms.chatpo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
