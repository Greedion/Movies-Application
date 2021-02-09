package com.project.service.review;

import com.project.entity.MovieEntity;
import com.project.exception.ExceptionsMessageArchive;
import com.project.model.Review;
import com.project.repository.ReviewRepository;
import com.project.service.like.LikeServiceImpl;
import com.project.entity.ReviewEntity;
import com.project.model.FullReview;
import com.project.repository.MovieRepository;
import com.project.utils.MapperForReview;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<FullReview>> getAllRecentForMovie(String movieID) {
        if (movieRepository.existsById(Long.parseLong(movieID))) {
            Optional<MovieEntity> movie = movieRepository.findById(Long.parseLong(movieID));
            if (movie.isPresent()) {
                List<ReviewEntity> reviewsForFilm;
                reviewsForFilm = reviewRepository.findAllByMovie(movie.get());
                List<FullReview> fullReviewObjects = new ArrayList<>();
                for (ReviewEntity x : reviewsForFilm
                ) {
                    fullReviewObjects.add(MapperForReview.entityToModel(x));
                }
                return ResponseEntity.ok(fullReviewObjects);
            }
        }
        logger.error(ExceptionsMessageArchive.REVIEW_S_PARSE_STRING_EXCEPTION);
        return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<HttpStatus> addReviewForMovie(Review iRM) {
        if (movieRepository.existsById(Long.parseLong(iRM.getMovieID()))) {
            Optional<MovieEntity> movie = movieRepository.findById(Long.parseLong(iRM.getMovieID()));
            movie.ifPresent(movieEntity -> reviewRepository.save(new ReviewEntity(null, iRM.getReview(), 0, movieEntity)));
            return ResponseEntity.ok().build();
        } else {
            logger.error(ExceptionsMessageArchive.REVIEW_S_ADD_REVIEW_NON_EXISTS_ID);
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<HttpStatus> deleteReviewForMovie(String reviewID) {
        if (reviewRepository.existsById(Long.parseLong(reviewID))) {
            reviewRepository.deleteById(Long.parseLong(reviewID));
            return ResponseEntity.ok().build();
        } else {
            logger.error(ExceptionsMessageArchive.REVIEW_S_REMOVE_REVIEW_NON_EXISTS_ID);
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<HttpStatus> likeReview(String reviewID) {
        if (reviewRepository.existsById(Long.parseLong(reviewID))) {
            Optional<ReviewEntity> review = reviewRepository.findById(Long.parseLong(reviewID));
            if (review.isPresent() && likeServiceImpl.canLike(2, review.get().getId())) {
                review.get().setLikeReview(review.get().getLikeReview() + 1);
                reviewRepository.save(review.get());
                return ResponseEntity.ok().build();
            }
        }
        logger.error(ExceptionsMessageArchive.REVIEW_S_ADD_LIKE_NON_EXISTS_ID);
        return ResponseEntity.badRequest().build();
    }
}
