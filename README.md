# player-management-service

Overview

This microservice provides a REST API to manage player data. It supports fetching a list of players and retrieving a player by their ID. The data is loaded from a CSV file during application startup.
Endpoints
1. Get All Players

   URL: /api/players

   Method: GET

   Parameters:
   page (optional): The page number (0-based index).
   size (optional): The number of items per page.
   sortProperty (optional): The property to sort by.
   sortDirection (optional): The direction to sort in (ASC or DESC).

   Response:
   200 OK: A paginated list of players.

2. Get Player by ID

   URL: /api/players/{id}

   Method: GET

   Path Variable:
   id: The unique identifier of the player.

   Response:
   200 OK: The player with the specified ID.
   404 Not Found: If the player with the specified ID does not exist.

Error Handling

The application includes global exception handling for the following scenarios:

    500 Internal Server Error: For general exceptions.
    400 Bad Request: For method argument type mismatches.
    Custom Exception Handling: For ResponseStatusException, with appropriate status and message.

Setup
Prerequisites

    Java 17
    Gradle

Installation

    Clone the repository:

    

git clone https://github.com/TzivyaSternbuch/player-management-service.git

Build the project using Gradle:

bash

./gradlew build

Run the application:


    ./gradlew bootRun


Project Structure

    PlayerController: Handles HTTP requests and responses for player data.
    PlayerService: Contains business logic for managing players.
    CsvLoaderService: Loads player data from a CSV file into the database.
    PlayerRepository: Interface for CRUD operations on player data.
    GlobalControllerExceptionHandler: Handles global exceptions and returns appropriate error responses.

Testing

To run tests, use the following Gradle command:


   ./gradlew test

