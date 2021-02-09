package com.project.model;

import lombok.*;

import javax.validation.constraints.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Review implements Serializable {

    @NotEmpty(message = "{Review.movieID.notEmpty}")
    @NotBlank(message = "{Review.movieID.notBlank}")
    @NotNull(message = "{Review.movieID.notNull}")
    @Pattern(regexp = "[0-9]+", message = "{Review.movieID.pattern}")
    private String movieID;

    @NotEmpty(message = "{Review.review.notEmpty}")
    @NotBlank(message = "{Review.review.notBlank=}")
    @NotNull(message =  "{Review.review.notNull}")
    @Size(min = 5, max = 500, message = "{Review.review.length}")
    private String review;
}
