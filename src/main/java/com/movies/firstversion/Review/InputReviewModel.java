package com.movies.firstversion.Review;
import lombok.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class InputReviewModel implements Serializable {

    @NotNull(message = "Movie id can't be null")
    @Pattern(regexp = "[0-9]+", message = "Accept only digits")
    String movieID;

    @NotNull(message = "Review can't be null")
    @Size(min = 5, max = 500, message = "The required length ranges from 5 - 500")
    String review;
}
