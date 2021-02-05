package com.project.controller;

import com.project.model.Movie;
import com.project.repository.MovieRepository;
import com.project.service.movie.MovieServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
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

    @ApiOperation(value = "Get all movies.")
    @GetMapping(produces = "application/json")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Movie>> getAllMovies() {
        return movieServiceImpl.returnAllMovie();
    }

    @ApiOperation(value = "Get a single movie details by id.")
    @GetMapping(value = "/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getMovieDetails(@PathVariable String id) {
        if (id == null) {
            logger.error("Attempt get movieDetails with empty input id.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt get movieDetails with empty input id.");
        }
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

    @ApiOperation(value = "Add movie.", notes = "Needed authorization from Admin account.")
    @PostMapping(produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addMovie(@Valid @RequestBody Movie movie, BindingResult result) {
        if (movie == null) {
            logger.error("Attempt create movie with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt create movie with empty input data.");
        } else if (movie.getDetails() == null ||
                movie.getTitle() == null) {
            logger.error("Attempt create movie with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt create movie with empty input data.");
        } else if (result.hasErrors()) {
            logger.error("Attempt to create movie with wrong data structure.");
            return new ResponseEntity<>(hadErrors(result), HttpStatus.BAD_REQUEST);
        } else {
            return movieServiceImpl.addMovie(movie);
        }
    }

    @ApiOperation(value = "Update movie.", notes = "Needed authorization from Admin account.")
    @PutMapping(produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateMovie(@Valid @RequestBody Movie movie, BindingResult result) {
        if (movie == null) {
            logger.error("Attempt update movie with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt create movie with empty input data.");
        } else if (movie.getDetails() == null ||
                movie.getTitle() == null || movie.getId() == null) {
            logger.error("Attempt update movie with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt update movie with empty input data.");
        } else if (result.hasErrors()) {
            logger.error("Attempt to update movie with wrong data structure.");
            return new ResponseEntity<>(hadErrors(result), HttpStatus.BAD_REQUEST);
        } else {
            return movieServiceImpl.updateMovie(movie);
        }
    }

    @ApiOperation(value = "Add mark.", notes = "Needed authentication.")
    @PostMapping(value = "/addrating/[{movieId},{rating}]", produces = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> addMark(@PathVariable(name = "movieId") String movieId, @PathVariable(name = "rating") String rating) {
        if (movieId == null || rating == null) {
            logger.error("Attempt add mark with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt add mark with empty input data.");
        }
        return movieServiceImpl.addRatingForFilm(movieId, rating);
    }

    @ApiOperation(value = "Add mark.", notes = "Needed authentication.")
    @PostMapping(value = "likemovie/{movieId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> addLikeMovie(@PathVariable(name = "movieId") String movieId) {
        if (movieId == null) {
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
