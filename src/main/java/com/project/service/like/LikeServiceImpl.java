package com.project.service.like;

import com.project.entity.LikeEntity;
import com.project.repository.LikeRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeInterface {

    private final LikeRepository likeRepository;
    public LikeServiceImpl(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public boolean canLike(int type, Long sourceID) {
        if (!likeRepository.existsByTypeAndSourceIDAndUsername(type, sourceID, getUsername())) {
            saveLike(type, sourceID);
            return true;
        } else return false;
    }

    private void saveLike(int type, Long sourceID) {
        likeRepository.save(new LikeEntity(type, sourceID, getUsername()));
    }

    private String getUsername() {
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
