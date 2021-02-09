package com.project.service.movie;

import com.project.model.Movie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MoveInterface {

    ResponseEntity<List<Movie>> returnAllMovie();

    ResponseEntity<Movie> addMovie(Movie movie);

    ResponseEntity<String> getDetails(String id);

    ResponseEntity<Movie> updateMovie(Movie inputMovie);

    ResponseEntity<HttpStatus> addRatingForFilm(String movieID, String mark);

    ResponseEntity<HttpStatus> likeMovie(String movieID);

}
