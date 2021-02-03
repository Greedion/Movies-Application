package com.Movies.Controller;

import com.Movies.Model.InputReviewModel;
import com.Movies.Repository.ReviewRepository;
import com.Movies.Service.Review.ReviewServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    private final Logger logger = LoggerFactory.getLogger(ReviewController.class);
    private final ReviewServiceImpl reviewServiceImpl;
    private final ReviewRepository reviewRepository;

    public ReviewController(ReviewServiceImpl reviewServiceImpl, ReviewRepository reviewRepository) {
        this.reviewServiceImpl = reviewServiceImpl;
        this.reviewRepository = reviewRepository;
    }

    @GetMapping(value = "/{movieID}", produces = "application/json")
    @PreAuthorize("permitAll()")
    ResponseEntity<?> getAllRecentForMovie(@PathVariable String movieID) {
        return reviewServiceImpl.getAllRecentForMovie(movieID);
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    ResponseEntity<?> addReviewForMovie(@RequestBody InputReviewModel inputReviewModel, BindingResult result) {

        if (inputReviewModel == null) {
            logger.error("Attempt create movie with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt create movie with empty input data.");
        } else if (inputReviewModel.getReview() == null || inputReviewModel.getMovieID() == null) {
            logger.error("Attempt create review with empty value.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt create review with empty review value.");
        } else if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : result.getFieldErrors()
            ) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }
        return reviewServiceImpl.addReviewForMovie(inputReviewModel);
    }

    @DeleteMapping("/{reviewId}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> deleteReviewForMovie(@PathVariable String reviewId) {
        try {
            if (reviewRepository.existsById(Long.parseLong(reviewId))){
                return reviewServiceImpl.deleteReviewForMovie(reviewId);
            } else{
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Expected data not found.");
            }
        } catch (NumberFormatException e) {
            logger.error("Attempt parse String to Long");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt parse String to Long.");
        }
    }

    @PutMapping(value = "/like/{reviewId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    ResponseEntity<?> likeReview(@PathVariable(name = "reviewId") String reviewId) {
        try {
            if (reviewRepository.existsById(Long.parseLong(reviewId))){
                return reviewServiceImpl.likeReview(reviewId);
            } else{
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Expected data not found.");
            }
        } catch (NumberFormatException e) {
            logger.error("Attempt parse String to Long");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt parse String to Long.");
        }
    }

}