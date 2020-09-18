package com.movies.firstversion.Movie.Service;
import com.movies.firstversion.Like.Service.LikeService;
import com.movies.firstversion.Movie.MapperForMovie;
import com.movies.firstversion.Movie.MovieEntity;
import com.movies.firstversion.Movie.MovieModel;
import com.movies.firstversion.Movie.MovieRepository;
import com.movies.firstversion.Rating.Service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
                    updatedMovie.setLikeMovie(moveFromDatabase.get().getLikeMovie());
                    updatedMovie.setRating(moveFromDatabase.get().getRating());
                    movieRepository.save(updatedMovie);
                    return ResponseEntity.ok("Updated");
                } else return ResponseEntity.badRequest().body("Movie with this id doesn't exist");
            } else return ResponseEntity.badRequest().body("Movie with this id doesn't exist");
        } else return ResponseEntity.badRequest().body("Id can't be null");
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
                            return ResponseEntity.ok("Added Rating");
                        } else ResponseEntity.badRequest().body("Problem with updating the baseline rating");
                    } else return ResponseEntity.badRequest().body("You have already added a rating for this movie");
                } else return ResponseEntity.badRequest().body("Movie with this id doesn't exist");
            } else return ResponseEntity.badRequest().body("Wrong rating value");
        } else return ResponseEntity.badRequest().body("This movie id doesn't exist");
        return ResponseEntity.badRequest().body("Something went wrong");
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
                    return ResponseEntity.ok("Added like");
                } else return ResponseEntity.badRequest().body("You already liked this movie");
            }
        }
        return ResponseEntity.badRequest().body("Wrong movie id");
    }

}
