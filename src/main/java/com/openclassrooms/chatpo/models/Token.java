package com.openclassrooms.chatpo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token extends AbstractEntity {

    @Column(columnDefinition = "TEXT")
    private String token;
    
    private LocalDateTime expiresAt;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;
}
