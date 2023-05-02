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
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    private String submittedBy;
    private Long restaurantId;
    private String review;

    private Integer peanutScore;
    private Integer eggScore;
    private Integer dairyScore;

    private ReviewStatus status;
}
/*
        "id":3,
        "submittedBy":"dummy",
        "restaurantId":"54545",
        "review":"test review",
        "peanutScore": 5,
        "eggScore":6,
        "dairyScore":7

 */