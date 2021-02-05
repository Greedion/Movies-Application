package com.project.utils;

import com.project.model.Movie;
import com.project.entity.MovieEntity;

public class MapperForMovie {

    public static Movie mapperEntityToModel(MovieEntity inputEntity) {
        return new Movie(String.valueOf(inputEntity.getId()),
                inputEntity.getTitle(),
                inputEntity.getDetails(),
                String.valueOf(inputEntity.getLikeMovie()),
                String.valueOf(inputEntity.getRating()));
    }

    public static MovieEntity mapperModelToEntity(Movie inputEntity) {
        return new MovieEntity(null, inputEntity.getTitle(),
                inputEntity.getDetails(),
                Integer.parseInt(inputEntity.getLikeMovie()),
                Double.parseDouble(inputEntity.getRating()));
    }
}
