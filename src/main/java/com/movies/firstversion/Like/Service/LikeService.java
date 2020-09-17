package com.movies.firstversion.Like.Service;
import com.movies.firstversion.Like.LikeEntity;
import com.movies.firstversion.Like.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LikeService {


    LikeRepository likeRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public boolean canLike(int type, Long sourceID) {
        if (!likeRepository.existsByTypeAndSourceIDAndUsername(type, sourceID, getUsername())) {
            saveLike(type, sourceID);
            return true;
        } else return false;
    }

    public void saveLike(int type, Long sourceID) {
        likeRepository.save(new LikeEntity(type, sourceID, getUsername()));
    }

    String getUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

}
