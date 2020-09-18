package com.movies.firstversion.Review;

public class MapperForReview {

    public static ReviewModel entityToModel(ReviewEntity inputEntity) {
        return new ReviewModel(String.valueOf(inputEntity.getId()),
                inputEntity.getReview(),
                String.valueOf(inputEntity.getLikeReview()),
                String.valueOf(inputEntity.getMovie().getTitle()));
    }
}
