package com.project.controller;

import com.project.exception.ExceptionsMessageArchive;
import com.project.model.Movie;
import com.project.repository.MovieRepository;
import com.project.service.movie.MovieServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<String> getMovieDetails(@PathVariable String id) {
        try {
            if (id == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        ExceptionsMessageArchive.MOVIE_C_ID_COULD_NOT_BE_NULL);
            } else if (movieRepository.existsById(Long.parseLong(id))) {
                return movieServiceImpl.getDetails(id);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        ExceptionsMessageArchive.MOVIE_C_NOT_FOUND_MOVIE_DETAILS_EXCEPTION);
            }
        } catch (NumberFormatException e) {
            logger.error(ExceptionsMessageArchive.MOVIE_C_PARSE_EXCEPTION);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    ExceptionsMessageArchive.MOVIE_C_PARSE_EXCEPTION);
        }
    }

    @ApiOperation(value = "Add movie.", notes = "Needed authorization from Admin account.")
    @PostMapping(produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Movie> addMovie(@Valid @RequestBody Movie movie) {
       return movieServiceImpl.addMovie(movie);
    }

    @ApiOperation(value = "Update movie.", notes = "Needed authorization from Admin account.")
    @PutMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Movie> updateMovie(@Valid @RequestBody Movie movie, @PathVariable String id) {
        movie.setId(Optional.ofNullable(id).orElse("0"));
        return movieServiceImpl.updateMovie(movie);
    }

    @ApiOperation(value = "Add mark.", notes = "Needed authentication.")
    @PostMapping(value = "/addrating/[{movieId},{rating}]", produces = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<HttpStatus> addMark(@PathVariable(name = "movieId") String movieId, @PathVariable(name = "rating") String rating) {
        if (movieId == null || rating == null) {
            logger.error(ExceptionsMessageArchive.MOVIE_C_ADD_MARK_EMPTY_INPUT_DATA_EXCEPTION);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    ExceptionsMessageArchive.MOVIE_C_ADD_MARK_EMPTY_INPUT_DATA_EXCEPTION);
        }
        return movieServiceImpl.addRatingForFilm(movieId, rating);
    }

    @ApiOperation(value = "Add like.", notes = "Needed authentication.")
    @PostMapping(value = "likemovie/{movieId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<HttpStatus> addLikeMovie(@PathVariable(name = "movieId") String movieId) {
        if (movieId == null) {
            logger.error(ExceptionsMessageArchive.MOVIE_C_ADD_LIKE_TO_MOVIE_WITH_NON_EXISTS_ID);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    ExceptionsMessageArchive.MOVIE_C_ADD_LIKE_TO_MOVIE_WITH_NON_EXISTS_ID);
        }
        return movieServiceImpl.likeMovie(movieId);
    }
}
