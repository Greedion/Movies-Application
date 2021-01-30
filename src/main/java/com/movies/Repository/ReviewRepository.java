package com.movies.Repository;
import com.movies.Entity.MovieEntity;
import com.movies.Entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    List<ReviewEntity> findAllByMovie(MovieEntity movie);
}
