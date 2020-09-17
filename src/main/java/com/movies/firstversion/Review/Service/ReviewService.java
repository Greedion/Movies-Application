package com.movies.firstversion.Review.Service;
import com.movies.firstversion.Like.Service.LikeService;
import com.movies.firstversion.Movie.MovieEntity;
import com.movies.firstversion.Movie.MovieRepository;
import com.movies.firstversion.Review.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    ReviewRepository reviewRepository;
    MovieRepository movieRepository;
    LikeService likeService;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, MovieRepository movieRepository, LikeService likeService) {
        this.reviewRepository = reviewRepository;
        this.movieRepository = movieRepository;
        this.likeService = likeService;
    }

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
        return ResponseEntity.badRequest().body("Movie id doesn't exist");
    }


    public ResponseEntity<?> addReviewForMovie(InputReviewModel iRM) {
        if (movieRepository.existsById(Long.parseLong(iRM.getMovieID()))) {
            Optional<MovieEntity> movie = movieRepository.findById(Long.parseLong(iRM.getMovieID()));
            movie.ifPresent(movieEntity -> reviewRepository.save(new ReviewEntity(null, iRM.getReview(), 0, movieEntity)));
            return ResponseEntity.ok("Created");
        } else return ResponseEntity.badRequest().body("Wrong Movie id");
    }

    public ResponseEntity<?> deleteReviewForMovie(String reviewID) {
        if (reviewRepository.existsById(Long.parseLong(reviewID))) {
            reviewRepository.deleteById(Long.parseLong(reviewID));
            return ResponseEntity.ok("Deleted");
        } else return ResponseEntity.badRequest().body("Wrong id");
    }

    public ResponseEntity<?> likeReview(String reviewID) {
        if (reviewRepository.existsById(Long.parseLong(reviewID))) {
            Optional<ReviewEntity> review = reviewRepository.findById(Long.parseLong(reviewID));
            if (review.isPresent()) {
                if (likeService.canLike(2, review.get().getId())) {
                    review.get().setLikeReview(review.get().getLikeReview() + 1);
                    reviewRepository.save(review.get());
                    return ResponseEntity.ok().body("Like added");
                } else return ResponseEntity.badRequest().body("You already liked this review");
            }
        }
        return ResponseEntity.badRequest().body("Wrong review id");
    }
}
