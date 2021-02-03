package com.Movies.Model;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReviewModel implements Serializable {

    private String id;

    @NotNull(message = "Review can't be null")
    @Size(min = 5, max = 500)
    private String review;

    @Pattern(regexp = "[0-9]+", message = "Accept only digits")
    private String likeReview;

    @NotNull(message = "Title can't be null")
    @NotEmpty(message = "Title can't be empty")
    @Size(min = 3, max = 14, message = "Name lenght can be 3-14")
    private String movie;

}
