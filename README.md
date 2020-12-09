
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
 - Spring Seciurity
 - Spring Validation
 - Spring Web
 - Spring Data JPA
 - H2 Database
 - Maven
 - Lombok
 
 
 # Api Endpoints: 
> Default adress: http://localhost:8080/
## Access for everyone
- login
- logout
### GET
- review/getAllByMovie
- movie/getAll


## Access for user (Credentials User/User) 
### POST
 - movie/addRating 
 - movie/likeMovie
 - review/like
 - review/addReviewForMovie
 - movie/getDetails

 
 ### DELETE
 - review/deleteReview
 
## Access for admin (Credentials Admin/Admin) 
### POST
- movie/add

### PUT
- movie/update


