package com.Movies.Model;

import lombok.*;

import javax.validation.constraints.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MovieModel implements Serializable {

    private String id;

    @NotNull(message = "Title can't be null")
    @NotEmpty(message = "Title can't be empty")
    @Size(min = 3, max = 14, message = "Tile length can be 3-14")
    private String title;

    @NotNull(message = "Title can't be null")
    @NotEmpty(message = "Title can't be empty")
    @Size(min = 3, max = 500, message = "Detail length can be from range 3-500")
    private String details;

    private String likeMovie;

    private String rating;

}
