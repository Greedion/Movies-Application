package com.movies.firstversion.Review;
import lombok.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class ReviewModel implements Serializable {

    String id;

    @NotNull(message = "Review can't be null")
    @Size(min = 5, max = 500)
    String review;

    @Pattern(regexp = "[0-9]+", message = "Accept only digits")
    String likeReview;

    @NotNull(message = "Title can't be null")
    @NotEmpty(message = "Title can't be empty")
    @Size(min = 3, max = 14, message = "Name lenght can be 3-14")
    String movie;

}
