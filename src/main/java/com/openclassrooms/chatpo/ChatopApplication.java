package com.openclassrooms.chatpo;

import com.openclassrooms.chatpo.models.Role;
import com.openclassrooms.chatpo.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
public class ChatopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatopApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName("USER").isEmpty()) {
                roleRepository.save(
                        Role.builder().name("USER").build()
                );
            }
        };
    }
}
