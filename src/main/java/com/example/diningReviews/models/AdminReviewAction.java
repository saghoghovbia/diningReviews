package com.example.diningReviews.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class AdminReviewAction {
    private Boolean accept;
}
