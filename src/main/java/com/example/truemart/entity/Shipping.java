package com.example.truemart.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Shipping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String country;

    private String firstName;

    private String lastName;

    private String companyName;

    private String address;

    private String address_option;

    private String city;

    private String email;

    private String phone;

}
