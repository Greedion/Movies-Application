package com.movies.firstversion.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {

   Boolean existsByTypeAndSourceIDAndUsername(Integer type, Long sourceID, String username);
}
