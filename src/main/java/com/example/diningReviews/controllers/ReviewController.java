package com.example.diningReviews.controllers;

import com.example.diningReviews.models.Restaurant;
import com.example.diningReviews.models.Review;
import com.example.diningReviews.models.ReviewStatus;
import com.example.diningReviews.models.User;
import com.example.diningReviews.repositories.RestaurantRepository;
import com.example.diningReviews.repositories.ReviewRepository;
import com.example.diningReviews.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    final private ReviewRepository reviewRepository;
    @Autowired
    final private RestaurantRepository restaurantRepository;
    @Autowired
    final private UserRepository userRepository;

    ReviewController(final ReviewRepository reviewRepo, RestaurantRepository restaurantRepo, UserRepository userRepo){
        this.reviewRepository = reviewRepo;
        this.restaurantRepository = restaurantRepo;
        this.userRepository = userRepo;
    }

    @PostMapping()
    public Review addNewReview(@RequestBody Review review){
        reviewerDetails(review);

        Optional<Restaurant> optionalRestaurant = this.restaurantRepository.findById(review.getRestaurantId());
        if(optionalRestaurant.isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        review.setStatus(ReviewStatus.PENDING);
        this.reviewRepository.save(review);

        return review;

    }

    private void reviewerDetails(Review newReview){
        if(ObjectUtils.isEmpty(newReview.getReview())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if(ObjectUtils.isEmpty(newReview.getRestaurantId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if(ObjectUtils.isEmpty(newReview.getPeanutScore())
        && ObjectUtils.isEmpty(newReview.getEggScore())
        && ObjectUtils.isEmpty(newReview.getDairyScore())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Optional<User> optionalUser = this.userRepository.findUserByDisplayName(newReview.getSubmittedBy());
        if(optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }



    }
}
