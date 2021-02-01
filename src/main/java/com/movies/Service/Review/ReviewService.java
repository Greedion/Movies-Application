package com.movies.Service.Review;

import com.movies.Model.InputReviewModel;
import org.springframework.http.ResponseEntity;

public interface ReviewService {

    ResponseEntity<?> getAllRecentForMovie(String movieID);

    ResponseEntity<?> addReviewForMovie(InputReviewModel iRM);

    ResponseEntity<?> deleteReviewForMovie(String reviewID);

    ResponseEntity<?> likeReview(String reviewID);

}
