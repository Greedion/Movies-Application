package com.project.model;

import lombok.*;

import javax.validation.constraints.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Movie implements Serializable {

    private String id;

    @NotBlank(message = "{Movie.title.notBlank}")
    @NotNull(message = "{Movie.title.notNull}")
    @NotEmpty(message = "{Movie.title.notEmpty}")
    @Size(min = 3, max = 14, message = "{Movie.title.length}")
    private String title;

    @NotBlank(message = "{Movie.details.notBlank}")
    @NotNull(message = "{Movie.details.notNull}")
    @NotEmpty(message = "{Movie.details.notEmpty}")
    @Size(min = 3, max = 500, message = "{Movie.details.length}")
    private String details;

    private String likeMovie;

    private String rating;

}
