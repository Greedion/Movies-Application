package com.movies.firstversion.Movie;

public class MapperForMovie {

    public static MovieModel mapperEntityToModel(MovieEntity mE) {
        return new MovieModel(String.valueOf(mE.getId()),
                mE.getTitle(),
                mE.getDetails(),
                String.valueOf(mE.getLikeMovie()),
                String.valueOf(mE.getRating()));
    }

    public static MovieEntity mapperModelToEntity(MovieModel movieModel) {
        return new MovieEntity(null, movieModel.getTitle(),
                movieModel.getDetails(),
                Integer.parseInt(movieModel.getLikeMovie()),
                Double.parseDouble(movieModel.getRating()));
    }
}
