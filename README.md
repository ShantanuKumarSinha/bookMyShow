# BookMyShow
This application shows how ricking works in bookmyshow with the help of spring boot and hibernate.

## Requirements
* User should be able to select the city
* User should be able to select the movie
* User should be able to select the theatre
* User should be able to select the show time
* User should be able to see available seats
* User should be able to select the seat type
* User should be able to select the number of seats
* Selected seat should be blocked till booking happens or release in case of failure or cancellation
* Booking should be done with the help of payment gateway
* Multiple payment options should be available
* Multiple payment can be done in a single ticket booking
* User should be a valid customer
* Only Valid customer can book the ticket

# Implement ratings functionality for movies
## Problem Statement
    You are building book my show. As a part of this system, you need to expose a functionality using which users can rate movies and check out average ratings of movies.

### Requirements
    We want to build 2 functionalities:

    1. Rate a movie
       - Users should be able to rate a movie on a scale of 1 to 5.
       - Once a user has rated a movie, they can update their rating.
       - Users cannot delete their rating for a movie.
       - Request for rating a movie will contain:
    
        - userId - Id of the user who is rating the movie.
        - movieId - Id of the movie which is being rated.
        - rating - Rating given by the user.
        - Response for rating a movie will contain:
    
        - rating object
        - response status - it will be SUCCESS or FAILURE
        - Make sure to add basic validations.
    
    2. Get average rating of a movie
        - Users should be able to get average rating of a movie.
        - Request for getting average rating of a movie will contain:
    
        - movieId - Id of the movie for which average rating is being requested.
        - Response for getting average rating of a movie will contain:
    
        - averageRating - Average rating of the movie.
        - response status - it will be SUCCESS or FAILURE

# Register user for Book My Show
## Problem Statement
    You are building book my show. As a part of this system, you need to expose a functionality using which users can sign up on the system and later can login to the system.

### Requirements
### Signup Requirements
    The request to signup will contain the following things:
    name - The name of the user
    email - The email of the user
    password - The password of the user
    We will not store users plain password in the database. You can use the BCryptPasswordEncoder class to generate the hash of the password.
    If a user already has signed up, then we should throw an error.
    If a user is registering themselves for the first time, then we should create a new user in the database and return the user details (only name, email and userId). Password should not be returned.
### Login Requirements
    The request to login will contain the following things:
    email - The email of the user
    password - The password of the user
    If the user is not registered, and they are trying to login, then we should throw an error.
    If the user is registered, and they are trying to login, then we should check if the password is correct or not. If the password is correct we should return isLoggedIn as true else false.
    Instructions
    Carefully look at SignupUserRequestDto, SignupUserResponseDto, LoginRequestDto and LoginResponseDto classes. These classes represent the request and response of the functionality which we want to implement.
    Carefully examine the models package to understand the database schema.
    Implement the signupUser and login method inside the TicketController.
    Implement the UserService interface and fix the repository interfaces.
    You might need to add annotations like @Service, @Autowired, @Entity etc. to make the solution work. You might also need to handle cardinality between the models.
    We will be using H2 database which is an in-memory SQL database. You do not need to implement any database related code. You just need to use the repository interfaces to interact with the database.