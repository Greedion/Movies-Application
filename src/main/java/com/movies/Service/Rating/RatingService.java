package com.movies.Service.Rating;
import com.movies.Repository.MovieRepository;
import com.movies.Entity.RatingEntity;
import com.movies.Repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RatingService {

    MovieRepository movieRepository;
    RatingRepository ratingRepository;

    @Autowired
    public RatingService(MovieRepository movieRepository, RatingRepository ratingRepository) {
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
    }

    public Double returnRateForFilm(Long movieID) {
        List<RatingEntity> allRatings = ratingRepository.findAllByMovieID(movieID);
        if (allRatings.isEmpty()) {
            return 0.0;
        } else {
            Double resultRating = 0.0;
            int count = 0;
            for (RatingEntity x : allRatings
            ) {
                count++;
                resultRating += x.getRating();
            }
            return resultRating / count;
        }

    }


    public boolean canAddRating(Long movieID, Double rating) {
        if (!ratingRepository.existsByUsernameAndMovieID(getUsername(), movieID)) {
            save(movieID, rating);
            return true;
        } else return false;
    }


    void save(Long movieID, Double rating) {
        ratingRepository.save(new RatingEntity(movieID, rating, getUsername()));
    }


    String getUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

}
