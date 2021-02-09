package com.project.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FullReview implements Serializable {

    private String id;

    @NotBlank(message = "{FullReview.review.notBlank}")
    @NotEmpty(message = "{FullReview.review.notEmpty}")
    @NotNull(message = "{FullReview.review.notNull}")
    @Length(min = 5, max = 500,message = "{FullReview.review.length}")
    @Size(min = 5, max = 500)
    private String review;

    @Pattern(regexp = "[0-9]+", message = "{FullReview.likeReview.pattern}")
    private String likeReview;

    @NotBlank(message = "{FullReview.movie.notBlank}")
    @NotEmpty(message = "{FullReview.movie.notEmpty}")
    @NotNull(message = "{FullReview.movie.notNull}")
    @Length(min = 5, max = 500,message = "{FullReview.movie.length}")
    private String movie;
}
