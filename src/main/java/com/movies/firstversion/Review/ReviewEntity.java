package com.movies.firstversion.Review;
import com.movies.firstversion.Movie.MovieEntity;
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
@Table(name = "REVIEW")
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    Long id;

    @Column(name = "REVIEW", nullable = false)
    String review;

    @Column(name = "LIIKEREVIEW")
    Integer likeReview;


    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "MOVIE_ID", referencedColumnName = "ID")
    private MovieEntity movie;

}
