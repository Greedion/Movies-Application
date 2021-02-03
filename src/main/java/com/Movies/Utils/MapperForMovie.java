package com.Movies.Utils;

import com.Movies.Model.MovieModel;
import com.Movies.Entity.MovieEntity;

public class MapperForMovie {

    public static MovieModel mapperEntityToModel(MovieEntity inputEntity) {
        return new MovieModel(String.valueOf(inputEntity.getId()),
                inputEntity.getTitle(),
                inputEntity.getDetails(),
                String.valueOf(inputEntity.getLikeMovie()),
                String.valueOf(inputEntity.getRating()));
    }

    public static MovieEntity mapperModelToEntity(MovieModel inputEntity) {
        return new MovieEntity(null, inputEntity.getTitle(),
                inputEntity.getDetails(),
                Integer.parseInt(inputEntity.getLikeMovie()),
                Double.parseDouble(inputEntity.getRating()));
    }
}
