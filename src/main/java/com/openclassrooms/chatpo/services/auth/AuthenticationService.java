package com.openclassrooms.chatpo.services.auth;

import com.openclassrooms.chatpo.dto.LoginRequestDto;
import com.openclassrooms.chatpo.dto.RegistrationRequestDto;
import com.openclassrooms.chatpo.models.Role;
import com.openclassrooms.chatpo.models.Token;
import com.openclassrooms.chatpo.models.User;
import com.openclassrooms.chatpo.repositories.RoleRepository;
import com.openclassrooms.chatpo.repositories.TokenRepository;
import com.openclassrooms.chatpo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public Token register(RegistrationRequestDto request) {
        Role userRole = roleRepository.findByName("USER")
                //Todo : creation d'une execption spéciale
                .orElseThrow(() -> new RuntimeException("User role not found"));

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(true)
                .roles(List.of(userRole))
                .build();

        userRepository.save(user);

        return generateAndSaveActivationToken(user);
    }

    public Token authenticate(LoginRequestDto request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );

        User user = (User) auth.getPrincipal();
        return generateAndSaveActivationToken(user);
    }

    private Token generateAndSaveActivationToken(User user) {

        //var claims = new HashMap<String, Object>();
        String jwtToken = jwtService.generateToken(user);

        Token token = Token.builder()
                .token(jwtToken)
                .expiresAt(jwtService
                        .extractExpiration(jwtToken)
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime()
                )
                .user(user)
                .build();

        return tokenRepository.save(token);
    }

}
