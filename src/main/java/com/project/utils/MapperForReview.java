package com.project.utils;

import com.project.entity.ReviewEntity;
import com.project.model.FullReview;

public class MapperForReview {

    public static FullReview entityToModel(ReviewEntity inputEntity) {
        return new FullReview(String.valueOf(inputEntity.getId()),
                inputEntity.getReview(),
                String.valueOf(inputEntity.getLikeReview()),
                String.valueOf(inputEntity.getMovie().getTitle()));
    }
}
