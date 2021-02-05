package com.project.service.movie;

import com.project.entity.MovieEntity;
import com.project.model.Movie;
import com.project.service.like.LikeServiceImpl;
import com.project.service.rating.RatingServiceImpl;
import com.project.utils.MapperForMovie;
import com.project.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MoveInterface {
    private final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);
    private final MovieRepository movieRepository;
    private final LikeServiceImpl likeServiceImpl;
    private final RatingServiceImpl ratingServiceImpl;
    private static final String INITIAL_RATING = "0.0";
    private static final String INITIAL_LIKE = "0";

    public MovieServiceImpl(MovieRepository movieRepository, LikeServiceImpl likeServiceImpl, RatingServiceImpl ratingServiceImpl) {
        this.movieRepository = movieRepository;
        this.likeServiceImpl = likeServiceImpl;
        this.ratingServiceImpl = ratingServiceImpl;
    }

    @Override
    public ResponseEntity<List<Movie>> returnAllMovie() {
        List<MovieEntity> allMovies = movieRepository.findAll();
        List<Movie> returnModelObjects = new ArrayList<>();
        for (MovieEntity x : allMovies
        ) {
            returnModelObjects.add(MapperForMovie.mapperEntityToModel(x));
        }
        return ResponseEntity.ok(returnModelObjects);
    }

    @Override
    public ResponseEntity<?> addMovie(Movie movie) {
        movie.setRating(INITIAL_RATING);
        movie.setLikeMovie(INITIAL_LIKE);
        MovieEntity movieEntity = MapperForMovie.mapperModelToEntity(movie);
        movieRepository.save(movieEntity);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> getDetails(String id) {
        if (movieRepository.existsById(Long.parseLong(id))) {
            Optional<MovieEntity> movie = movieRepository.findById(Long.parseLong(id));
            if (movie.isPresent()) {
                return ResponseEntity.ok(movie.get().getDetails());
            }
        }
        logger.error("Attempt to get the details using a non-existent ID.");
        return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<?> updateMovie(Movie inputMovie) {
        if (inputMovie.getId() != null && !inputMovie.getId().equals("")) {
            if (movieRepository.existsById(Long.parseLong(inputMovie.getId()))) {
                Optional<MovieEntity> moveFromDatabase = movieRepository.findById(Long.parseLong(inputMovie.getId()));
                if (moveFromDatabase.isPresent()) {
                    MovieEntity updatedMovie = new MovieEntity();
                    updatedMovie.setId(moveFromDatabase.get().getId());
                    if (inputMovie.getTitle() == null || inputMovie.getTitle().equals("")) {
                        updatedMovie.setTitle(moveFromDatabase.get().getTitle());
                    } else {
                        updatedMovie.setTitle(inputMovie.getTitle());
                    }
                    if (inputMovie.getDetails() == null || inputMovie.getDetails().equals("")) {
                        updatedMovie.setDetails(moveFromDatabase.get().getDetails());
                    } else {
                        updatedMovie.setDetails(inputMovie.getDetails());
                    }
                    updatedMovie.setLikeMovie(moveFromDatabase.get().getLikeMovie());
                    updatedMovie.setRating(moveFromDatabase.get().getRating());
                    movieRepository.save(updatedMovie);
                    return ResponseEntity.ok().build();
                }
            }
        }
        logger.error("Attempt to update movie with null variable's");
        return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<?> addRatingForFilm(String movieID, String mark) {
        if (movieRepository.existsById(Long.parseLong(movieID))) {
            double convertedMark = Double.parseDouble(mark);
            if (allowForRating(convertedMark)) {
                Optional<MovieEntity> movie = movieRepository.findById(Long.parseLong(movieID));
                if (movie.isPresent()) {
                    movie.ifPresent(movieEntity -> movieEntity.setRating(Double.parseDouble(mark)));
                    if (ratingServiceImpl.canAddRating(movie.get().getId(), Double.parseDouble(mark))) {
                        if (updatedBaseRate(movie.get().getId())) {
                            return ResponseEntity.ok().build();
                        }
                    }
                }
            }
        }
        logger.error("Attempt to add rating to movie.");
        return ResponseEntity.badRequest().build();
    }


    private boolean allowForRating(double rating) {
        return rating >= 1 && rating <= 5;
    }

    private boolean updatedBaseRate(Long movieID) {
        Double baseRating = ratingServiceImpl.returnRateForFilm(movieID);
        Optional<MovieEntity> movie = movieRepository.findById(movieID);
        if (movie.isPresent()) {
            movie.get().setRating(baseRating);
            movieRepository.save(movie.get());
            return true;
        }
        return false;
    }

    @Override
    public ResponseEntity<?> likeMovie(String movieID) {
        if (movieRepository.existsById(Long.parseLong(movieID))) {
            Optional<MovieEntity> movie = movieRepository.findById(Long.parseLong(movieID));
            if (movie.isPresent()) {
                if (likeServiceImpl.canLike(1, movie.get().getId())) {
                    movie.get().setLikeMovie(movie.get().getLikeMovie() + 1);
                    movieRepository.save(movie.get());
                    return ResponseEntity.ok().build();
                }
            }
        }
        logger.error("Attempt to like movie.");
        return ResponseEntity.badRequest().build();
    }

}
