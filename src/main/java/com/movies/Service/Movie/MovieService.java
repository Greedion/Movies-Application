package com.movies.Service.Movie;

import com.movies.Entity.MovieEntity;
import com.movies.Model.MovieModel;
import com.movies.Service.Like.LikeService;
import com.movies.Service.Rating.RatingService;
import com.movies.Utils.MapperForMovie;
import com.movies.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    MovieRepository movieRepository;
    LikeService likeService;
    RatingService ratingService;
    private final String INITIAL_RATING = "0.0";
    private final String INITIAL_LIKE = "0";

    @Autowired
    public MovieService(MovieRepository movieRepository, LikeService likeService, RatingService ratingService) {
        this.movieRepository = movieRepository;
        this.likeService = likeService;
        this.ratingService = ratingService;
    }

    public ResponseEntity<?> returnAllMovie() {
        List<MovieEntity> allMovies = movieRepository.findAll();
        List<MovieModel> returnModelObjects = new ArrayList<>();
        for (MovieEntity x : allMovies
        ) {
            returnModelObjects.add(MapperForMovie.mapperEntityToModel(x));
        }
        return ResponseEntity.ok(returnModelObjects);
    }

    public ResponseEntity<?> addMovie(MovieModel movieModel) {
        movieModel.setRating(INITIAL_RATING);
        movieModel.setLikeMovie(INITIAL_LIKE);
        MovieEntity movieEntity = MapperForMovie.mapperModelToEntity(movieModel);
        movieRepository.save(movieEntity);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> getDetails(String id) {
        if (movieRepository.existsById(Long.parseLong(id))) {
            Optional<MovieEntity> movie = movieRepository.findById(Long.parseLong(id));
            if (movie.isPresent()) {
                return ResponseEntity.ok(movie.get().getDetails());
            }
        }
        return ResponseEntity.badRequest().build();
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
                    updatedMovie.setLikeMovie(moveFromDatabase.get().getLikeMovie());
                    updatedMovie.setRating(moveFromDatabase.get().getRating());
                    movieRepository.save(updatedMovie);
                    return ResponseEntity.ok().build();
                }
            }
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<?> addRatingForFilm(String movieID, String mark) {
        if (movieRepository.existsById(Long.parseLong(movieID))) {
            double convertedMark = Double.parseDouble(mark);
            if (allowForRating(convertedMark)) {
                Optional<MovieEntity> movie = movieRepository.findById(Long.parseLong(movieID));
                if (movie.isPresent()) {
                    movie.ifPresent(movieEntity -> movieEntity.setRating(Double.parseDouble(mark)));
                    if (ratingService.canAddRating(movie.get().getId(), Double.parseDouble(mark))) {
                        if (updatedBaseRate(movie.get().getId())) {
                            return ResponseEntity.ok().build();
                        }
                    }
                }
            }
        }
        return ResponseEntity.badRequest().build();
    }


    boolean allowForRating(double rating) {
        return rating >= 1 && rating <= 5;
    }

    boolean updatedBaseRate(Long movieID) {
        Double baseRating = ratingService.returnRateForFilm(movieID);
        Optional<MovieEntity> movie = movieRepository.findById(movieID);
        if (movie.isPresent()) {
            movie.get().setRating(baseRating);
            movieRepository.save(movie.get());
            return true;
        }
        return false;
    }

    public ResponseEntity<?> likeMovie(String movieID) {
        if (movieRepository.existsById(Long.parseLong(movieID))) {
            Optional<MovieEntity> movie = movieRepository.findById(Long.parseLong(movieID));
            if (movie.isPresent()) {
                if (likeService.canLike(1, movie.get().getId())) {
                    movie.get().setLikeMovie(movie.get().getLikeMovie() + 1);
                    movieRepository.save(movie.get());
                    return ResponseEntity.ok().build();
                }
            }
        }
        return ResponseEntity.badRequest().build();
    }

}