package com.movies.firstversion.Movie;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MOVIE")
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    Long id;

    @Column(name = "TITLE",nullable = false)
    String title;

    @Column(name = "DETAILS", nullable = false)
    String details;

    @Column(name = "LIIKEMOVIE")
    Integer likeMovie;

    @Column(name = "RATING", nullable = false)
    Double rating;


}
