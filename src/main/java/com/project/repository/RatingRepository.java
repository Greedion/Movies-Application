package com.project.repository;

import com.project.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

    List<RatingEntity> findAllByMovieID(Long movieID);

    Boolean existsByUsernameAndMovieID(String username, Long movieID);
}
