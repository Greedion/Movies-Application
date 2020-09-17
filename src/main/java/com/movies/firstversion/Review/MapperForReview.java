package com.movies.firstversion.Review;

public class MapperForReview {

    public static ReviewModel entityToModel(ReviewEntity rE) {
        return new ReviewModel(String.valueOf(rE.getId()),
                rE.getReview(),
                String.valueOf(rE.getLikeReview()),
                String.valueOf(rE.getMovie().getTitle()));
    }
}
