package com.movies.firstversion.Review;

import lombok.*;

import javax.validation.constraints.Digits;
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

    @NotNull
    @Pattern(regexp = "[0-9]+", message = "Accept only digits")
    String movieID;

    @NotNull
    @Size(min = 5, max = 500)
    String review;
}
