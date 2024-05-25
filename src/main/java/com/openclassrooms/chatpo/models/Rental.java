package com.openclassrooms.chatpo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Rental extends AbstractEntity{

    private String name;
    private BigDecimal surface;
    private BigDecimal price;
    private String picture;
    private String description;

    @Column(name = "owner_id")
    private Integer ownerId;

    @ManyToOne
    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    private User owner;

    @OneToMany(mappedBy = "rental")
    private List<Message> messages;
}
