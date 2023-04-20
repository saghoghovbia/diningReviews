package com.example.diningReviews.controllers;

import com.example.diningReviews.models.Restaurant;
import com.example.diningReviews.repositories.RestaurantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    final private RestaurantRepository restaurantRepository;

    RestaurantController(final RestaurantRepository restaurantRepo){
        this.restaurantRepository = restaurantRepo;
    }


    @PostMapping()
    public Restaurant addNewRestaurant(@RequestBody Restaurant restaurant){
        restaurantInfoFillForm(restaurant);

        this.restaurantRepository.save(restaurant);

        return restaurant;
    }
    @GetMapping()
    public Restaurant getRestaurantById(@RequestParam("id") Long id){
        Optional<Restaurant> optionalRestaurant = this.restaurantRepository.findById(id);

        if(optionalRestaurant.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Restaurant restaurant = optionalRestaurant.get();

        return restaurant;
    }

    @GetMapping()
    public Iterable<Restaurant> getRestaurantByZip(@RequestParam("zip") String zipCode, @RequestParam(value = "Allergy", required = false) String allergy){
        Iterable<Restaurant> restaurants = null;

        if(!allergy.isEmpty()){
          String letter = allergy.substring(0,1).toLowerCase();
            switch (letter){
                case "p":
                    restaurants = this.restaurantRepository.findRestaurantByZipCodeAndPeanutAlNotNullOrderByPeanutAl(zipCode);
                    break;
                case "e":
                    restaurants = this.restaurantRepository.findRestaurantByZipCodeAndEggAlNotNullOrderByEggAl(zipCode);
                    break;
                case "d":
                    restaurants = this.restaurantRepository.findRestaurantByZipCodeAndDairyAlNotNullOrderByDairyAl(zipCode);
                    break;
                default:
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }

        return restaurants;

    }


    private void restaurantInfoFillForm(Restaurant restaurant){
        restaurantValidation(restaurant.getName(), restaurant.getZipcode());

        if(ObjectUtils.isEmpty(restaurant.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if(ObjectUtils.isEmpty(restaurant.getZipcode())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if(ObjectUtils.isEmpty(restaurant.getCity())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if(ObjectUtils.isEmpty(restaurant.getState())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if(ObjectUtils.isEmpty(restaurant.getPhoneNumber())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if(ObjectUtils.isEmpty(restaurant.getEmailAddress())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    private void restaurantValidation(String name, String zipCode){
        Optional<Restaurant> optionalRestaurant = this.restaurantRepository.findRestaurantByNameAndZipCode(name, zipCode);

        if(optionalRestaurant.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

    }
}
