package com.Movies.Entity;
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
@Table(name = "RATING")
public class RatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "MOVIEID", nullable = false)
    private Long movieID;

    @Column(name = "RATE", nullable = false)
    private Double rating;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    public RatingEntity(Long movieID, Double rating, String username) {
        this.movieID = movieID;
        this.rating = rating;
        this.username = username;
    }
}
