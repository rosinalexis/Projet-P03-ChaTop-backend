package com.openclassrooms.chatpo.services.auth;

import com.openclassrooms.chatpo.dto.LoginRequestDto;
import com.openclassrooms.chatpo.models.Token;
import com.openclassrooms.chatpo.models.User;
import com.openclassrooms.chatpo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public Token authenticate(LoginRequestDto request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = (User) auth.getPrincipal();
        return generateToken(user);
    }

    public Token generateToken(User user) {

        String jwtToken = jwtService.generateToken(user);

        return Token.builder()
                .token(jwtToken)
                .user(user)
                .build();
    }

}
