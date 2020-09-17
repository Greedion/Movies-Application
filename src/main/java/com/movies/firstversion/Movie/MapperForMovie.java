package com.movies.firstversion.Movie;

public class MapperForMovie {

    public static MovieModel mapperEntityToModel(MovieEntity mE) {
        return new MovieModel(String.valueOf(mE.getId()),
                mE.getTitle(),
                mE.getDetails(),
                String.valueOf(mE.getLikeMovie()),
                String.valueOf(mE.getRating()));
    }

    public static MovieEntity mapperModelToEntity(MovieModel mM) {
        return new MovieEntity(null, mM.getTitle(),
                mM.getDetails(),
                Integer.parseInt(mM.getLikeMovie()),
                Double.parseDouble(mM.getRating()));
    }
}
