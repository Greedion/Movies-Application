package com.movies.firstversion.Movie.Controller;


import com.movies.firstversion.Movie.MovieModel;
import com.movies.firstversion.Movie.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MovieController {

    MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }


    @GetMapping(value = "movie/getAll")
  ResponseEntity<?> getAllMovies(){
        return movieService.returnAllMovie();
  }

  @PostMapping(value = "movie/add")
    ResponseEntity<?> addMovie(@RequestBody MovieModel movieModel){
        return movieService.addMovie(movieModel);
  }

  @PostMapping(value = "movie/getDetails")
    ResponseEntity<?> getMovieDetails(@RequestParam String id){
        return movieService.getDetails(id);
  }

  @PutMapping(value = "movie/update")
    ResponseEntity<?> updateMovie(@RequestBody MovieModel movieModel){
      System.out.println(movieModel.getId());
        return movieService.updateMovie(movieModel);
  }

  @PostMapping(value = "movie/addMark")
    ResponseEntity<?> addMark(@RequestParam String movieID, @RequestParam String mark){
        return movieService.addMarkForFilm(movieID, mark);
  }




}
