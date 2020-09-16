package com.movies.firstversion.Review.Service;

import com.movies.firstversion.Movie.MovieEntity;
import com.movies.firstversion.Movie.MovieRepository;
import com.movies.firstversion.Review.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    ReviewRepository reviewRepository;
    MovieRepository movieRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, MovieRepository movieRepository) {
        this.reviewRepository = reviewRepository;
        this.movieRepository = movieRepository;
    }

    ResponseEntity<?> getAllRecentForMovie(String movieID) {
        if (movieRepository.existsById(Long.parseLong(movieID))) {
            Optional<MovieEntity> movie = movieRepository.findById(Long.parseLong(movieID));
            if(movie.isPresent()) {
                List<ReviewEntity> reviewsForFilm;
                reviewsForFilm = reviewRepository.findAllByMovie(movie.get());
                List<ReviewModel> reviewModelObjects = new ArrayList<>();
                for (ReviewEntity x: reviewsForFilm
                     ) {
                    reviewModelObjects.add(MapperForReview.entityToModel(x));
                }
                return ResponseEntity.ok(reviewModelObjects);
            }
        }
        return ResponseEntity.badRequest().body("Movie id doesn't exist");
    }


    ResponseEntity<?> addReviewForMovie(InputReviewModel iRM){
        if(movieRepository.existsById(Long.parseLong(iRM.getMovieId()))){
            Optional<MovieEntity> movie = movieRepository.findById(Long.parseLong(iRM.getMovieId()));
            movie.ifPresent(movieEntity -> reviewRepository.save(new ReviewEntity(null, iRM.getReview(), 0, movieEntity)));
            return ResponseEntity.ok("Created");
        } else return ResponseEntity.badRequest().body("Wrong Movie id");
    }
    ResponseEntity<?> deleteReviewForMovie(String reviewID){
        if(reviewRepository.existsById(Long.parseLong(reviewID))){
            reviewRepository.deleteById(Long.parseLong(reviewID));
            return ResponseEntity.ok("Deleted");
        } else return ResponseEntity.badRequest().body("Wrong id");
    }
}
