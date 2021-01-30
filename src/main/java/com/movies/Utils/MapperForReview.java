package com.movies.Utils;

import com.movies.Entity.ReviewEntity;
import com.movies.Model.ReviewModel;

public class MapperForReview {

    public static ReviewModel entityToModel(ReviewEntity inputEntity) {
        return new ReviewModel(String.valueOf(inputEntity.getId()),
                inputEntity.getReview(),
                String.valueOf(inputEntity.getLikeReview()),
                String.valueOf(inputEntity.getMovie().getTitle()));
    }
}
