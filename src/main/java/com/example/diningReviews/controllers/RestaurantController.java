package com.example.diningReviews.controllers;

import com.example.diningReviews.models.Restaurant;
import com.example.diningReviews.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;


@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    final private RestaurantRepository restaurantRepository;

    private final Pattern randomZip = Pattern.compile("\\d{5}");

    RestaurantController(final RestaurantRepository restaurantRepo){
        this.restaurantRepository = restaurantRepo;
    }


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant addNewRestaurant(@RequestBody Restaurant restaurant){
        validateNewRestaurant(restaurant);

        this.restaurantRepository.save(restaurant);

        return restaurant;

    }

    @GetMapping()
    public Iterable<Restaurant> getAllRestuarants(){
       // Restaurant restaurant = new Restaurant();
        //restaurantData(restaurant);
        return this.restaurantRepository.findAll();
        
    }
    @GetMapping("{id}")
    public Restaurant getRestaurant(@PathVariable("id") Long id){
        Optional<Restaurant> optionalRestaurant = this.restaurantRepository.findById(id);

        if(optionalRestaurant.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Restaurant restaurant = optionalRestaurant.get();

        return restaurant;
    }

    @GetMapping("/search")
    public Iterable<Restaurant> getRestaurantByZip(@RequestParam("zip") String zipCode, @RequestParam(value = "Allergy", required = false) String allergy){
        Iterable<Restaurant> restaurants = new ArrayList<Restaurant>();

        if(!allergy.isEmpty()){
          String letter = allergy.substring(0,1).toLowerCase();
            switch (letter){
                case "p":
                    restaurants = this.restaurantRepository.findRestaurantByZipCodeAndPeanutScoreNotNullOrderByPeanutScore(zipCode);
                    break;
                case "e":
                    restaurants = this.restaurantRepository.findRestaurantByZipCodeAndEggScoreNotNullOrderByEggScore(zipCode);
                    break;
                case "d":
                    restaurants = this.restaurantRepository.findRestaurantByZipCodeAndDairyScoreNotNullOrderByDairyScore(zipCode);
                    break;
                default:
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }

        return restaurants;

    }

    @DeleteMapping("/{id}")
    public Restaurant removeRestaurant(@PathVariable Long id){
        Optional<Restaurant> optionalRestaurant = this.restaurantRepository.findById(id);

        if(optionalRestaurant.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Restaurant restaurant = optionalRestaurant.get();

        this.restaurantRepository.delete(restaurant);

        return restaurant;
    }


    private void restaurantInfoFillForm(Restaurant restaurant){
        restaurantValidation(restaurant.getName(), restaurant.getZipCode());

        if(ObjectUtils.isEmpty(restaurant.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if(ObjectUtils.isEmpty(restaurant.getZipCode())){
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

    private void validateNewRestaurant(Restaurant restaurant) {
        if (ObjectUtils.isEmpty(restaurant.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //validateZipCode(restaurant.getZipCode());

        Optional<Restaurant> existingRestaurant = restaurantRepository.findRestaurantByNameAndZipCode(restaurant.getName(), restaurant.getZipCode());
        if (existingRestaurant.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    /*
    private void validateZipCode(String zipcode) {
        if (!randomZip.matcher(zipcode).matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

     */

    /*
    private void restaurantData(Restaurant restaurant){
        restaurant.setId(83L);
        restaurant.setName("Denny's");
        restaurant.setCity("Houston");
        restaurant.setState("Texas");
        restaurant.setZipCode("77073");
        restaurant.setPhoneNumber("281ddfffdf");
        restaurant.setEmailAddress("dfj@email.com");
        restaurant.setEggScore(3);
        restaurant.setDairyScore(4);
        restaurant.setPeanutScore(5);
        restaurant.setOverallScore(12);
        
        this.restaurantRepository.save(restaurant);
    }
     */
}
