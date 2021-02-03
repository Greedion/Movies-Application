package com.Movies.Utils;

import com.Movies.Entity.ReviewEntity;
import com.Movies.Model.ReviewModel;

public class MapperForReview {

    public static ReviewModel entityToModel(ReviewEntity inputEntity) {
        return new ReviewModel(String.valueOf(inputEntity.getId()),
                inputEntity.getReview(),
                String.valueOf(inputEntity.getLikeReview()),
                String.valueOf(inputEntity.getMovie().getTitle()));
    }
}
