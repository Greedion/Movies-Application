package com.project.service.review;

import com.project.model.Review;
import org.springframework.http.ResponseEntity;

public interface ReviewInterface {

    ResponseEntity<?> getAllRecentForMovie(String movieID);

    ResponseEntity<?> addReviewForMovie(Review iRM);

    ResponseEntity<?> deleteReviewForMovie(String reviewID);

    ResponseEntity<?> likeReview(String reviewID);

}
