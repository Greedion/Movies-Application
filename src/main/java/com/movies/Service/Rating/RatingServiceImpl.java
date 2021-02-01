package com.movies.Service.Rating;

import com.movies.Entity.RatingEntity;
import com.movies.Repository.RatingRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {

        this.ratingRepository = ratingRepository;
    }

    @Override
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

    /**
     * addRatingForFilm method from Movie service needs it.
     */
    @SuppressWarnings("It needs be public")
    public boolean canAddRating(Long movieID, Double rating) {
        if (!ratingRepository.existsByUsernameAndMovieID(getUsername(), movieID)) {
            save(movieID, rating);
            return true;
        } else return false;
    }

    private void save(Long movieID, Double rating) {
        ratingRepository.save(new RatingEntity(movieID, rating, getUsername()));
    }

    private String getUsername() {
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