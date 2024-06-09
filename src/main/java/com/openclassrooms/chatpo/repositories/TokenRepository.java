package com.openclassrooms.chatpo.repositories;

import com.openclassrooms.chatpo.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    Optional<Token> findByToken(String token);
}
