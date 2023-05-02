package com.example.diningReviews.controllers;

import com.example.diningReviews.models.Restaurant;
import com.example.diningReviews.models.Review;
import com.example.diningReviews.models.ReviewStatus;
import com.example.diningReviews.repositories.RestaurantRepository;
import com.example.diningReviews.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ReviewsStatus")
public class AdminReviewController {

    final private ReviewRepository reviewRepository;
    final private RestaurantRepository restaurantRepository;

    @Autowired
    AdminReviewController(final ReviewRepository reviewRepo, RestaurantRepository restaurantRepo){
        this.reviewRepository = reviewRepo;
        this.restaurantRepository = restaurantRepo;
    }

    @GetMapping("/{restaurant}/{status}") //TODO: Switch this up to RequestParams for the String status
    public List<Review> getApprovedReviewsByRestaurant(@PathVariable String restaurantName, String zipCode, @PathVariable String status){
        Optional<Restaurant> optionalRestaurant = this.restaurantRepository.findRestaurantByNameAndZipCode(restaurantName, zipCode);
        if(optionalRestaurant.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Restaurant restaurant = optionalRestaurant.get();

        switch (status.toUpperCase()){
            case "PENDING":
                return this.reviewRepository.findReviewByRestaurantIdAndStatus(restaurant.getId(), ReviewStatus.PENDING);
            case "ACCEPTED":
                return this.reviewRepository.findReviewByRestaurantIdAndStatus(restaurant.getId(),ReviewStatus.ACCEPTED);
            case "REJECTED":
                return this.reviewRepository.findReviewByRestaurantIdAndStatus(restaurant.getId(),ReviewStatus.REJECTED);
            default:
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }

    @GetMapping("/{status}") //TODO: Switch this up to RequestParams for the String status
    public List<Review> getReviewsByStatus(@PathVariable String status){

        switch (status.toUpperCase()){
            case "PENDING":
                return this.reviewRepository.findReviewByStatus(ReviewStatus.PENDING);
            case "ACCEPTED":
                return this.reviewRepository.findReviewByStatus(ReviewStatus.ACCEPTED);
            case "REJECTED":
                return this.reviewRepository.findReviewByStatus(ReviewStatus.REJECTED);
            default:
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }


    }

    @PutMapping("/{status}")
    public Review editReviewStatus(@RequestBody Review review, String status ){
        Optional<Review> optionalReview = this.reviewRepository.findById(review.getId());

        if(optionalReview.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        switch (status.toUpperCase()){
            case "PENDING":
                 review.setStatus(ReviewStatus.PENDING);
            case "ACCEPTED":
                review.setStatus(ReviewStatus.ACCEPTED);
            case "REJECTED":
                review.setStatus(ReviewStatus.REJECTED);
                default:
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }


    }


}
