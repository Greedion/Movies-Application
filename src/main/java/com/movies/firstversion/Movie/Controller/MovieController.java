package com.movies.firstversion.Movie.Controller;


import com.movies.firstversion.Movie.MovieModel;
import com.movies.firstversion.Movie.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MovieController {

    MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }


    @GetMapping(value = "movie/getAll")
    ResponseEntity<?> getAllMovies() {
        return movieService.returnAllMovie();
    }

    @PostMapping(value = "movie/add")
    ResponseEntity<?> addMovie(@RequestBody MovieModel movieModel, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(hadErrors(result), HttpStatus.BAD_REQUEST);
        }
        return movieService.addMovie(movieModel);
    }

    @PostMapping(value = "movie/getDetails")
    ResponseEntity<?> getMovieDetails(@RequestParam String id) {
        return movieService.getDetails(id);
    }

    @PutMapping(value = "movie/update")
    ResponseEntity<?> updateMovie(@RequestBody MovieModel movieModel, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(hadErrors(result), HttpStatus.BAD_REQUEST);
        }
        return movieService.updateMovie(movieModel);
    }

    @PostMapping(value = "movie/addMark")
    ResponseEntity<?> addMark(@RequestParam String movieID, @RequestParam String mark) {
        return movieService.addMarkForFilm(movieID, mark);
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
