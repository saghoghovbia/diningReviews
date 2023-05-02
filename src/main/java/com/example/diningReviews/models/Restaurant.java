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
public class Restaurant {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String city;
    private String state;
    private String zipCode;

    private String phoneNumber;
    private String emailAddress;

    private Integer overallScore;
    private Integer peanutScore;
    private Integer eggScore;
    private Integer dairyScore;
}
