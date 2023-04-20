package com.example.diningReviews.repositories;

import com.example.diningReviews.models.Review;
import com.example.diningReviews.models.ReviewStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review,Long> {
    List<Review> findReviewByRestaurantIdAndStatus(Long id, ReviewStatus reviewStatus);

    List<Review> findReviewByStatus(ReviewStatus reviewStatus);
}
