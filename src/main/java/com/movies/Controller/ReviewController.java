package com.movies.Controller;
import com.movies.Model.InputReviewModel;
import com.movies.Service.Review.ReviewServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/review/")
public class ReviewController {

    private final ReviewServiceImpl reviewServiceImpl;

    public ReviewController(ReviewServiceImpl reviewServiceImpl) {
        this.reviewServiceImpl = reviewServiceImpl;
    }

    @GetMapping(value = "getAllByMovie/{movieID}")
    ResponseEntity<?> getAllRecentForMovie(@PathVariable String movieID) {
        return reviewServiceImpl.getAllRecentForMovie(movieID);
    }

    @PostMapping(value = "addReviewForMovie")
    ResponseEntity<?> addReviewForMovie(@RequestBody InputReviewModel inputReviewModel, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : result.getFieldErrors()
            ) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }
        return reviewServiceImpl.addReviewForMovie(inputReviewModel);
    }

    @DeleteMapping(value = "deleteReview")
    ResponseEntity<?> deleteReviewForMovie(@RequestParam String reviewID) {
        return reviewServiceImpl.deleteReviewForMovie(reviewID);
    }

    @PostMapping(value = "like")
    ResponseEntity<?> likeReview(String reviewID) {
        return reviewServiceImpl.likeReview(reviewID);
    }

}
