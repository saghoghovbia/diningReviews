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
    private Long id;

    private String displayName;

    private String city;
    private String state;
    private String zipCode;

    private Boolean peanutAllergy;
    private Boolean eggAllergy;
    private Boolean dairyAllergy;

}

/*
        "id":1,
        "name":"dummy display",
        "city":"test city",
        "state":"test state",
        "zipCode":"test zip",
        "peanutAl":"test phone",
        "eggAl":"test email",
        "dairyAl": false
        spring.jpa.properties.hibernate.globally_quoted_identifiers=true
        spring.jpa.properties.hibernate.globally_quoted_identifiers_skip_column_definitions = true
*/
