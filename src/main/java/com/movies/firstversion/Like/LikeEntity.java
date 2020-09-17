package com.movies.firstversion.Like;
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
@Table(name = "LIIKE")
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    Long id;

    // 1 - Movie , 2 Review
    @Column(name = "TYPE", nullable = false)
    Integer type;

    // id for movie or review
    @Column(name = "SOURCEID", nullable = false)
    Long sourceID;

    @Column(name = "USERNAME", nullable = false)
    String username;

    public LikeEntity(int type, Long sourceID, String username) {
        this.type = type;
        this.sourceID = sourceID;
        this.username = username;
    }
}
