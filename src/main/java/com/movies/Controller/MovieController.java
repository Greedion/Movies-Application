package com.movies.Controller;
import com.movies.Model.MovieModel;
import com.movies.Service.Movie.MovieServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/movie/")
public class MovieController {

    final
    private MovieServiceImpl movieServiceImpl;

    public MovieController(MovieServiceImpl movieServiceImpl) {
        this.movieServiceImpl = movieServiceImpl;
    }

    @GetMapping(value = "getAll")
    ResponseEntity<?> getAllMovies() {
        return movieServiceImpl.returnAllMovie();
    }

    @PostMapping(value = "add")
    ResponseEntity<?> addMovie(@RequestBody MovieModel movieModel, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(hadErrors(result), HttpStatus.BAD_REQUEST);
        }
        return movieServiceImpl.addMovie(movieModel);
    }

    @GetMapping(value = "getDetails/{id}")
    ResponseEntity<?> getMovieDetails(@PathVariable String id) {
        return movieServiceImpl.getDetails(id);
    }

    @PutMapping(value = "update")
    ResponseEntity<?> updateMovie(@RequestBody MovieModel movieModel, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(hadErrors(result), HttpStatus.BAD_REQUEST);
        }
        return movieServiceImpl.updateMovie(movieModel);
    }

    @PostMapping(value = "addRating")
    ResponseEntity<?> addMark(@RequestParam String movieID, @RequestParam String rating) {
        return movieServiceImpl.addRatingForFilm(movieID, rating);
    }

    @PostMapping(value = "likeMovie")
    ResponseEntity<?> addLikeMovie(@RequestParam String movieID) {
        return movieServiceImpl.likeMovie(movieID);
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
