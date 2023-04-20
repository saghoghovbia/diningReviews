package com.example.diningReviews.repositories;

import com.example.diningReviews.models.Restaurant;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends CrudRepository<Restaurant,Long> {

    Optional<Restaurant> findRestaurantByNameAndZipCode(String name, String zipCode);

    List<Restaurant> findRestaurantByZipCodeAndPeanutAlNotNullOrderByPeanutAl(String zipCode);

    List<Restaurant> findRestaurantByZipCodeAndEggAlNotNullOrderByEggAl(String zipCode);

    List<Restaurant> findRestaurantByZipCodeAndDairyAlNotNullOrderByDairyAl(String zipCode);
}
