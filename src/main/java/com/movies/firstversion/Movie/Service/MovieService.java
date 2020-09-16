package com.movies.firstversion.Movie.Service;

import com.movies.firstversion.Movie.MappeForMovie;
import com.movies.firstversion.Movie.MovieEntity;
import com.movies.firstversion.Movie.MovieModel;
import com.movies.firstversion.Movie.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {


    MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public ResponseEntity<?> returnAllMovie() {
        List<MovieEntity> allMovies = movieRepository.findAll();
        List<MovieModel> returnModelObjects = new ArrayList<>();
        for (MovieEntity x : allMovies
        ) {
            returnModelObjects.add(MappeForMovie.mapperEntityToModel(x));
        }
        return ResponseEntity.ok(returnModelObjects);
    }

    public ResponseEntity<?> addMovie(MovieModel movieModel) {
        MovieEntity movieEntity = MappeForMovie.mapperModelToEntity(movieModel);
        movieRepository.save(movieEntity);
        return ResponseEntity.ok("Created");
    }

    public ResponseEntity<?> getDetails(String id) {
        if (movieRepository.existsById(Long.parseLong(id))) {
            Optional<MovieEntity> movie = movieRepository.findById(Long.parseLong(id));
            if (movie.isPresent()) {
                return ResponseEntity.ok(movie.get().getDetails());
            }
        }
        return ResponseEntity.badRequest().body("Wrong id");
    }

    public ResponseEntity<?> updateMovie(MovieModel inputMovieModel) {
        if (inputMovieModel.getId() != null && !inputMovieModel.getId().equals("")) {
            if (movieRepository.existsById(Long.parseLong(inputMovieModel.getId()))) {
                Optional<MovieEntity> moveFromDatabase = movieRepository.findById(Long.parseLong(inputMovieModel.getId()));
                if (moveFromDatabase.isPresent()) {
                    MovieEntity updatedMovie = new MovieEntity();
                    updatedMovie.setId(moveFromDatabase.get().getId());
                    if (inputMovieModel.getTitle() == null || inputMovieModel.getTitle().equals("")) {
                        updatedMovie.setTitle(moveFromDatabase.get().getTitle());
                    } else {
                        updatedMovie.setTitle(inputMovieModel.getTitle());
                    }
                    if (inputMovieModel.getDetails() == null || inputMovieModel.getDetails().equals("")) {
                        updatedMovie.setDetails(moveFromDatabase.get().getDetails());
                    } else {
                        updatedMovie.setDetails(inputMovieModel.getDetails());
                    }
                    if (inputMovieModel.getLikeMovie() == null || inputMovieModel.getLikeMovie().equals("")) {
                        updatedMovie.setLikeMovie(moveFromDatabase.get().getLikeMovie());
                    } else {
                        updatedMovie.setLikeMovie(Integer.parseInt(inputMovieModel.getLikeMovie()));
                    }
                    if (inputMovieModel.getRating() == null || inputMovieModel.getRating().equals("")) {
                        updatedMovie.setRating(moveFromDatabase.get().getRating());
                    } else {
                        updatedMovie.setRating(Double.parseDouble(inputMovieModel.getRating()));
                    }
                    movieRepository.save(updatedMovie);
                    return ResponseEntity.ok("Updated");

                } else return ResponseEntity.badRequest().body("Movie with this id doesn't exist");
            } else return ResponseEntity.badRequest().body("Movie with this id doesn't exist");
        } else return ResponseEntity.badRequest().body("Id can't be null");
    }

    public ResponseEntity<?> addMarkForFilm(String movieID, String mark) {
        if (movieRepository.existsById(Long.parseLong(movieID))) {
            Double convertedMark = Double.parseDouble(mark);
            if (allowForMark(convertedMark)) {
                Optional<MovieEntity> movie = movieRepository.findById(Long.parseLong(movieID));
                movie.ifPresent(movieEntity -> movieEntity.setRating(Double.parseDouble(mark)));
                movieRepository.save(movie.get());
                return ResponseEntity.ok("Added Rating");
            }
            return ResponseEntity.badRequest().body("Wrong rating value");
        }
        return ResponseEntity.badRequest().body("This movie id doesn't exist");
    }


    boolean allowForMark(double mark) {
        return mark >= 1 && mark <= 5;
    }

    ResponseEntity<?> likeMovie(String movieID) {
        if (movieRepository.existsById(Long.parseLong(movieID))) {
            Optional<MovieEntity> movie = movieRepository.findById(Long.parseLong(movieID));
            if (movie.isPresent()) {
                movie.get().setLikeMovie(movie.get().getLikeMovie() + 1);
                movieRepository.save(movie.get());
                return ResponseEntity.ok("Added like");
            }
        }
        return ResponseEntity.badRequest().body("Wrong movie id");
    }



}
