package com.example.diningReviews.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long Id;

    private String displayName;

    private String city;
    private String state;
    private String zipCode;

    private Boolean peanutAl;
    private Boolean eggAl;
    private Boolean dairyAl;

}
