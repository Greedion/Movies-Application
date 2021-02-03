package com.Movies.Service.Review;

import com.Movies.Model.InputReviewModel;
import org.springframework.http.ResponseEntity;

public interface ReviewInterface {

    ResponseEntity<?> getAllRecentForMovie(String movieID);

    ResponseEntity<?> addReviewForMovie(InputReviewModel iRM);

    ResponseEntity<?> deleteReviewForMovie(String reviewID);

    ResponseEntity<?> likeReview(String reviewID);

}
