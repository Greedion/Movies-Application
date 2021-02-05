package com.Movies.Controller;

import com.Movies.Model.MovieModel;
import com.Movies.Repository.MovieRepository;
import com.Movies.Service.Movie.MovieServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    private final Logger logger = LoggerFactory.getLogger(MovieController.class);
    private final MovieServiceImpl movieServiceImpl;
    private final MovieRepository movieRepository;

    public MovieController(MovieServiceImpl movieServiceImpl,
                           MovieRepository movieRepository) {
        this.movieServiceImpl = movieServiceImpl;
        this.movieRepository = movieRepository;
    }

    @GetMapping(produces = "application/json")
    @PreAuthorize("permitAll()")
    ResponseEntity<?> getAllMovies() {
        return movieServiceImpl.returnAllMovie();
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("permitAll()")
    ResponseEntity<?> getMovieDetails(@PathVariable String id) {
        try {
            if (movieRepository.existsById(Long.parseLong(id))) {
                return movieServiceImpl.getDetails(id);
            } else {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Expected data not found.");
            }
        } catch (NumberFormatException e) {
            logger.error("Attempt parse String to Long");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt parse String to Long.");
        }
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> addMovie(@RequestBody MovieModel movieModel, BindingResult result) {
        if (movieModel == null) {
            logger.error("Attempt create movie with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt create movie with empty input data.");
        } else if (movieModel.getDetails() == null ||
                movieModel.getTitle() == null) {
            logger.error("Attempt create movie with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt create movie with empty input data.");
        } else if (result.hasErrors()) {
            logger.error("Attempt to create movie with wrong data structure.");
            return new ResponseEntity<>(hadErrors(result), HttpStatus.BAD_REQUEST);
        } else {
            return movieServiceImpl.addMovie(movieModel);
        }
    }

    @PutMapping(produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> updateMovie(@RequestBody MovieModel movieModel, BindingResult result) {
        if (movieModel == null) {
            logger.error("Attempt update movie with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt create movie with empty input data.");
        } else if (movieModel.getDetails() == null ||
                movieModel.getTitle() == null || movieModel.getId() == null) {
            logger.error("Attempt update movie with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt update movie with empty input data.");
        } else if (result.hasErrors()) {
            logger.error("Attempt to update movie with wrong data structure.");
            return new ResponseEntity<>(hadErrors(result), HttpStatus.BAD_REQUEST);
        } else {
            return movieServiceImpl.updateMovie(movieModel);
        }
    }

    @PostMapping(value = "/addrating/[{movieId},{rating}]", produces = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    ResponseEntity<?> addMark(@PathVariable(name = "movieId") String movieId, @PathVariable(name = "rating") String rating) {
        if(movieId == null || rating == null){
            logger.error("Attempt add mark with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt add mark with empty input data.");
        }
        return movieServiceImpl.addRatingForFilm(movieId, rating);
    }

    @PostMapping(value = "likemovie/{movieId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    ResponseEntity<?> addLikeMovie(@PathVariable(name = "movieId") String movieId) {
        if(movieId == null){
            logger.error("Attempt add like to movie with empty movie id value.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt add like to movie with empty movie id value.");
        }
        return movieServiceImpl.likeMovie(movieId);
    }

    Map<String, String> hadErrors(BindingResult result) {
        Map<String, String> errorMap = new HashMap<>();

        for (FieldError error : result.getFieldErrors()
        ) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }
        return errorMap;
    }
}
