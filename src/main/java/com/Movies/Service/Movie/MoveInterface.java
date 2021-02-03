package com.Movies.Service.Movie;

import com.Movies.Model.MovieModel;
import org.springframework.http.ResponseEntity;

public interface MoveInterface {

    ResponseEntity<?> returnAllMovie();

    ResponseEntity<?> addMovie(MovieModel movieModel);

    ResponseEntity<?> getDetails(String id);

    ResponseEntity<?> updateMovie(MovieModel inputMovieModel);

    ResponseEntity<?> addRatingForFilm(String movieID, String mark);

    ResponseEntity<?> likeMovie(String movieID);

}
