package com.library_rental_system.rental.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Street is required")
    private String street;
    @NotNull(message = "City is required")
    private String city;
    @NotNull(message = "Country is required")
    private String country;
    @NotNull(message = "Description is required")
    private String description;
    private String zipCode;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    @JsonIgnore // to avoid infinite recursion
    private List<User> users = new ArrayList<>();


    
}
