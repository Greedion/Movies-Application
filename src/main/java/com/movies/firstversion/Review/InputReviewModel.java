package com.movies.firstversion.Review;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class InputReviewModel implements Serializable {

    String movieId;

    String review;
}
