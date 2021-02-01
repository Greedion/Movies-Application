package com.movies.Service.Movie;

import com.movies.Model.MovieModel;
import org.springframework.http.ResponseEntity;

public interface MoveService {

    ResponseEntity<?> returnAllMovie();

    ResponseEntity<?> addMovie(MovieModel movieModel);

    ResponseEntity<?> getDetails(String id);

    ResponseEntity<?> updateMovie(MovieModel inputMovieModel);

    ResponseEntity<?> addRatingForFilm(String movieID, String mark);

    ResponseEntity<?> likeMovie(String movieID);

}
