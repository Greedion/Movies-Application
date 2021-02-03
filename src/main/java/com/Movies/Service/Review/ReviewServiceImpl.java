package com.Movies.Service.Review;

import com.Movies.Entity.MovieEntity;
import com.Movies.Model.InputReviewModel;
import com.Movies.Repository.ReviewRepository;
import com.Movies.Service.Like.LikeServiceImpl;
import com.Movies.Entity.ReviewEntity;
import com.Movies.Model.ReviewModel;
import com.Movies.Repository.MovieRepository;
import com.Movies.Utils.MapperForReview;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewInterface {

    private final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);
    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final LikeServiceImpl likeServiceImpl;

    public ReviewServiceImpl(ReviewRepository reviewRepository, MovieRepository movieRepository, LikeServiceImpl likeServiceImpl) {
        this.reviewRepository = reviewRepository;
        this.movieRepository = movieRepository;
        this.likeServiceImpl = likeServiceImpl;
    }

    @Override
    public ResponseEntity<?> getAllRecentForMovie(String movieID) {
        if (movieRepository.existsById(Long.parseLong(movieID))) {
            Optional<MovieEntity> movie = movieRepository.findById(Long.parseLong(movieID));
            if (movie.isPresent()) {
                List<ReviewEntity> reviewsForFilm;
                reviewsForFilm = reviewRepository.findAllByMovie(movie.get());
                List<ReviewModel> reviewModelObjects = new ArrayList<>();
                for (ReviewEntity x : reviewsForFilm
                ) {
                    reviewModelObjects.add(MapperForReview.entityToModel(x));
                }
                return ResponseEntity.ok(reviewModelObjects);
            }
        }
        logger.error("Attempt parse String movieId to Long");
        return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<?> addReviewForMovie(InputReviewModel iRM) {
        if (movieRepository.existsById(Long.parseLong(iRM.getMovieID()))) {
            Optional<MovieEntity> movie = movieRepository.findById(Long.parseLong(iRM.getMovieID()));
            movie.ifPresent(movieEntity -> reviewRepository.save(new ReviewEntity(null, iRM.getReview(), 0, movieEntity)));
            return ResponseEntity.ok().build();
        } else{
            logger.error("Attempt to add the review using a non-existent ID.");
            return ResponseEntity.badRequest().build();}
    }

    @Override
    public ResponseEntity<?> deleteReviewForMovie(String reviewID) {
        if (reviewRepository.existsById(Long.parseLong(reviewID))) {
            reviewRepository.deleteById(Long.parseLong(reviewID));
            return ResponseEntity.ok().build();
        } else{
            logger.error("Attempt to delete the review using a non-existent ID.");
            return ResponseEntity.badRequest().build();}
    }

    @Override
    public ResponseEntity<?> likeReview(String reviewID) {
        if (reviewRepository.existsById(Long.parseLong(reviewID))) {
            Optional<ReviewEntity> review = reviewRepository.findById(Long.parseLong(reviewID));
            if (review.isPresent()) {
                if (likeServiceImpl.canLike(2, review.get().getId())) {
                    review.get().setLikeReview(review.get().getLikeReview() + 1);
                    reviewRepository.save(review.get());
                    return ResponseEntity.ok().build();
                }
            }
        }
        logger.error("Attempt to like the review using a non-existent ID.");
        return ResponseEntity.badRequest().build();
    }
}
