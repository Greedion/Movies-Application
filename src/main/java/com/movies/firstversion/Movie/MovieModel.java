package com.movies.firstversion.Movie;


import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.*;


import javax.validation.constraints.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
public class MovieModel implements Serializable {


    String id;

    @NotNull(message = "Title can't be null")
    @NotEmpty(message = "Title can't be empty")
    @Size(min = 3, max = 14, message = "Name lenght can be 3-14")
    String title;

    @NotNull(message = "Title can't be null")
    @NotEmpty(message = "Title can't be empty")
    @Size(min = 3, max = 500, message = "Title lenght can be from range 3-500")
    String details;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "[0-9]+", message = "Accept only digits")
    String likeMovie;

    @NotNull
    @NotEmpty
    @Min(value = 1, message = "Rating can't be less than 1.0")
    @Max(value = 5, message = "Rating cannot be higher than 5.0 ")
    String rating;


}
