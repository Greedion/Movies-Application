package com.Movies.Repository;
import com.Movies.Entity.MovieEntity;
import com.Movies.Entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    List<ReviewEntity> findAllByMovie(MovieEntity movie);
}
