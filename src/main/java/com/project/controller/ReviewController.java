package com.project.controller;

import com.project.exception.ExceptionsMessageArchive;
import com.project.model.FullReview;
import com.project.model.Review;
import com.project.repository.ReviewRepository;
import com.project.service.review.ReviewServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

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

    @ApiOperation(value = "Get all reviews.")
    @GetMapping(value = "/{movieID}", produces = "application/json")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<FullReview>> getAllRecentForMovie(@PathVariable String movieID) {
        return reviewServiceImpl.getAllRecentForMovie(movieID);
    }

    @ApiOperation(value = "Get a single movie details by id.", notes = "Needed authentication.")
    @PostMapping(produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<HttpStatus> addReviewForMovie(@Valid @RequestBody Review review) {
        return reviewServiceImpl.addReviewForMovie(review);
    }

    @ApiOperation(value = "Delete review.", notes = "Needed authorization from Admin account.")
    @DeleteMapping("/{reviewId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteReviewForMovie(@PathVariable String reviewId) {
        try {
            if (reviewId == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        ExceptionsMessageArchive.REVIEW_C_ID_COULD_NOT_BE_NULL);
            } else if (reviewRepository.existsById(Long.parseLong(reviewId))) {
                return reviewServiceImpl.deleteReviewForMovie(reviewId);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        ExceptionsMessageArchive.REVIEW_C_EXPECTED_DATA_FOR_REMOVE_REVIEW_NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            logger.error(ExceptionsMessageArchive.REVIEW_C_PARSE_EXCEPTION);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    ExceptionsMessageArchive.REVIEW_C_PARSE_EXCEPTION);
        }
    }

    @ApiOperation(value = "Like review.", notes = "Needed authentication.")
    @PutMapping(value = "/like/{reviewId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<HttpStatus> likeReview(@PathVariable(name = "reviewId") String reviewId) {
        try {
            if (reviewRepository.existsById(Long.parseLong(reviewId))){
                return reviewServiceImpl.likeReview(reviewId);
            } else{
                throw new ResponseStatusException(HttpStatus.NO_CONTENT,
                        ExceptionsMessageArchive.REVIEW_C_EXPECTED_DATA_NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            logger.error(ExceptionsMessageArchive.REVIEW_C_PARSE_EXCEPTION);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    ExceptionsMessageArchive.REVIEW_C_PARSE_EXCEPTION);
        }
    }
}
