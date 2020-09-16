package com.movies.firstversion.Review.Controller;

import com.movies.firstversion.Review.InputReviewModel;
import com.movies.firstversion.Review.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewController {

    ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping(value = "review/getAllByMovie")
    ResponseEntity<?> getAllRecentForMovie(@RequestParam String movieID){
        return reviewService.getAllRecentForMovie(movieID);
    }

    @PostMapping(value = "review/addReviewForMovie")
    ResponseEntity<?> addReviewForMovie(@RequestBody  InputReviewModel inputReviewModel){
        return reviewService.addReviewForMovie(inputReviewModel);
    }

    @DeleteMapping(value = "review/deleteReview")
    ResponseEntity<?> deleteReviewForMovie(@RequestParam String reviewID){
        return reviewService.deleteReviewForMovie(reviewID);
    }

    @PostMapping(value = "review/like")
    ResponseEntity<?> likeReview(String reviewID){
        return reviewService.likeReview(reviewID);
    }

}
