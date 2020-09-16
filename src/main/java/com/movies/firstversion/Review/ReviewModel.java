package com.movies.firstversion.Review;

import com.movies.firstversion.Movie.MovieEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class ReviewModel implements Serializable {

    String id;

    String review;

    String likeReview;

    String movie;

}
