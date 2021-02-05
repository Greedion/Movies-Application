package com.project.service.movie;

import com.project.model.Movie;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MoveInterface {

    ResponseEntity<List<Movie>> returnAllMovie();

    ResponseEntity<?> addMovie(Movie movie);

    ResponseEntity<?> getDetails(String id);

    ResponseEntity<?> updateMovie(Movie inputMovie);

    ResponseEntity<?> addRatingForFilm(String movieID, String mark);

    ResponseEntity<?> likeMovie(String movieID);

}
