package com.project.service.review;

import com.project.model.FullReview;
import com.project.model.Review;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReviewInterface {

    ResponseEntity<List<FullReview>> getAllRecentForMovie(String movieID);

    ResponseEntity<HttpStatus> addReviewForMovie(Review iRM);

    ResponseEntity<HttpStatus> deleteReviewForMovie(String reviewID);

    ResponseEntity<HttpStatus> likeReview(String reviewID);

}
