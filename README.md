
# Movies-Application  

> Application for movie fan

### Clone

- Clone this repo to your local machine using `https://github.com/Greedion/Movies-Application.git`



## Features
- View the list of all movies
- Adding the movie to the base
- View movie details
- Movie data update
- View a list of reviews for a given movie
- Adding a review of the movie
- Removing the review of a given movie
- Adding a movie rating (1-5)
- Like a movie
- Like a review
- Automatic rating calculation based on all rates
- Block against adding more than one likes / reviews for a given movie
- Possibility to log in (2 types of accounts)

## Technology
 - Spring Boot
 - Spring Security
 - Spring Validation
 - Spring Web
 - Spring Data JPA
 - H2 Database
 - Maven
 - Lombok
 
 # Credentials
 ## Admin/Admin
 ## User/User
 
 # Api Endpoints: 
> Default adress: http://localhost:8080/api
## Access for everyone
### Authorization
- POST /auth/signin
### Create account
- POST /user/createaccount
### Get movie's
- GET /movie
- GET /movie/{id}
### Get review's
- GET /review
- GET /review/{id}
## Access for user
- POST /movie/addrating/[{movieId},{rating}]
- POST /movie/likemovie/{movieId}
- POST /review
- POST /review/like/{reviewId}
###Access for admin
- POST /movie
- PUT /movie
- DELETE /review/{id}
- GET /user









