package com.project.service.movie;

import com.project.entity.MovieEntity;
import com.project.exception.ExceptionsMessageArchive;
import com.project.model.Movie;
import com.project.service.like.LikeServiceImpl;
import com.project.service.rating.RatingServiceImpl;
import com.project.utils.MapperForMovie;
import com.project.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Movie> addMovie(Movie movie) {
        movie.setRating(ExceptionsMessageArchive.MOVIE_S_INITIAL_RATING);
        movie.setLikeMovie(ExceptionsMessageArchive.MOVIE_S_INITIAL_LIKE);
        MovieEntity movieEntity = MapperForMovie.mapperModelToEntity(movie);
        Movie objectForReturn = MapperForMovie.mapperEntityToModel(movieRepository.save(movieEntity));
        return ResponseEntity.ok(objectForReturn);
    }

    @Override
    public ResponseEntity<String> getDetails(String id) {
        if (movieRepository.existsById(Long.parseLong(id))) {
            Optional<MovieEntity> movie = movieRepository.findById(Long.parseLong(id));
            if (movie.isPresent()) {
                return ResponseEntity.ok(movie.get().getDetails());
            }
        }
        logger.error(ExceptionsMessageArchive.MOVIE_S_UPDATE_WITH_NON_EXISTS_ID);
        return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<Movie> updateMovie(Movie inputMovie) {

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
                    Movie objectForReturn = MapperForMovie.mapperEntityToModel(movieRepository.save(updatedMovie));
                    return ResponseEntity.ok(objectForReturn);
                }
            }
        logger.error(ExceptionsMessageArchive.MOVIE_S_UPDATE_WITH_NON_EXISTS_ID);
        return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<HttpStatus> addRatingForFilm(String movieID, String mark) {
        if (movieRepository.existsById(Long.parseLong(movieID))) {
            double convertedMark = Double.parseDouble(mark);
            if (allowForRating(convertedMark)) {
                Optional<MovieEntity> movie = movieRepository.findById(Long.parseLong(movieID));
                if (movie.isPresent()) {
                    movie.ifPresent(movieEntity -> movieEntity.setRating(Double.parseDouble(mark)));
                    if (ratingServiceImpl.canAddRating(movie.get().getId(), Double.parseDouble(mark)) &&updatedBaseRate(movie.get().getId())) {
                            return ResponseEntity.ok().build();
                        }
                }
            }
        }
        logger.error(ExceptionsMessageArchive.MOVIE_S_ADD_RATING_EXCEPTION);
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
    public ResponseEntity<HttpStatus> likeMovie(String movieID) {
        if (movieRepository.existsById(Long.parseLong(movieID))) {
            Optional<MovieEntity> movie = movieRepository.findById(Long.parseLong(movieID));
            if (movie.isPresent() && likeServiceImpl.canLike(1, movie.get().getId())) {
                movie.get().setLikeMovie(movie.get().getLikeMovie() + 1);
                movieRepository.save(movie.get());
                return ResponseEntity.ok().build();
            }
        }
        logger.error(ExceptionsMessageArchive.MOVIE_S_ADD_LIKE_EXCEPTION);
        return ResponseEntity.badRequest().build();
    }
}
