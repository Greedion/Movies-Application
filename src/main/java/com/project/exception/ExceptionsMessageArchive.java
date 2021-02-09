package com.project.exception;

public class ExceptionsMessageArchive {

    /**
     * Movie Controller
     */
    public static final String MOVIE_C_ID_COULD_NOT_BE_NULL = "Id could not be null";
    public static final String MOVIE_C_NOT_FOUND_MOVIE_DETAILS_EXCEPTION =
            "Attempt to get movie details by id that does not exist in database.";
    public static final String MOVIE_C_PARSE_EXCEPTION = "Attempt parse String to Long.";
    public static final String MOVIE_C_ADD_MARK_EMPTY_INPUT_DATA_EXCEPTION =
            "Attempt add mark with empty input data.";
    public static final String MOVIE_C_ADD_LIKE_TO_MOVIE_WITH_NON_EXISTS_ID = "Attempt add like to movie with empty movie id value.";

    /**
     * Review Controller
     */
    public static final String REVIEW_C_ID_COULD_NOT_BE_NULL = "Id could not be null";
    public static final String REVIEW_C_EXPECTED_DATA_FOR_REMOVE_REVIEW_NOT_FOUND = "Attempt to remove review for movie by id that does not exist in database.";
    public static final String REVIEW_C_PARSE_EXCEPTION = "Attempt parse String to Long.";
    public static final String REVIEW_C_EXPECTED_DATA_NOT_FOUND = "Expected data not found.";

    /**
     * Global Exception Handler
     */
    public static final String G_E_HANDLER_VALIDATION_FAILED_MESSAGE ="Validation Failed.";

    /**
     * Auth Entry Point Jwt (security package)
     */
    public static final String AEP_JWT_UNAUTHORIZED_ERROR_LOG = "Unauthorized error: {}";
    public static final String AEP_JWT_UNAUTHORIZED_ERROR = "Error: Unauthorized";

    /**
     * JWT Filter
     */
    public static final String JWT_F_SET_USER_AUTHENTICATION_EXCEPTION = "Cannot set user authentication: {}";

    /**
     * JWT Utils
     */
    public static final String JWT_U_INVALID_JWT_TOKEN_LOG = "Invalid JWT token: {}";
    public static final String JWT_U_JWT_TOKEN__EXPIRED_LOG = "JWT token is expired: {}";
    public static final String JWT_U_JWT_TOKEN_IS_UNSUPPORTED = "JWT token is unsupported: {}";
    public static final String JWT_U_JWT_TOKEN_CLAIMS_STRING_IS_EMPTY = "JWT claims string is empty: {}";

    /**
     * Authorization Service
     */
    public static final String AUTH_U_INCORRECT_DATA_EXCEPTION = "Received incorrect logging data.";

    /**
     * Movie Service
     */
    public static final String MOVIE_S_INITIAL_RATING = "0.0";
    public static final String MOVIE_S_INITIAL_LIKE = "0";
    public static final String MOVIE_S_UPDATE_WITH_NON_EXISTS_ID = "Attempt to update movie by id that does not exist in database.";
    public static final String MOVIE_S_ADD_RATING_EXCEPTION = "Attempt to add rating to movie.";
    public static final String MOVIE_S_ADD_LIKE_EXCEPTION = "Attempt to add like to movie.";

    /**
     * Review Service
     */
    public static final String REVIEW_S_PARSE_STRING_EXCEPTION = "Attempt parse String movieId to Long";
    public static final String REVIEW_S_ADD_REVIEW_NON_EXISTS_ID = "Attempt to add the review using a non-existent ID.";
    public static final String REVIEW_S_REMOVE_REVIEW_NON_EXISTS_ID = "Attempt to delete the review using a non-existent ID.";
    public static final String REVIEW_S_ADD_LIKE_NON_EXISTS_ID = "Attempt to like the review using a non-existent ID.";

    /**
     * User Service
     */
    public static final String USER_S_DEFAULT_USER_ROLE = "ROLE_USER";
    public static final String USER_S_WRONG_DEFAULT_ROLE = "Received wrong DEFAULT_USER_ROLE or account with this username doesn't exist";

}
