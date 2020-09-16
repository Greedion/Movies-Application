package com.movies.firstversion.Movie;

import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
public class MovieModel implements Serializable {

    String id;

    String title;

    String details;

    String likeMovie;

    String rating;
}
