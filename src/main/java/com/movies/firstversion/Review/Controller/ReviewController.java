package com.movies.firstversion.Review.Controller;

import com.movies.firstversion.Review.InputReviewModel;
import com.movies.firstversion.Review.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    ResponseEntity<?> addReviewForMovie(@RequestBody  InputReviewModel inputReviewModel, BindingResult result){
        if(result.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : result.getFieldErrors()
            ) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }
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
