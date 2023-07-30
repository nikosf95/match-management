# Match Management API

The Match Management API is a Spring Boot application that provides basic CRUD operations for managing match data.

## Getting Started

To get started with this project, follow the instructions below.

### Prerequisites

- Docker
- Docker-Compose

### Installation

1. Clone the repository:
2. Navigate to the project directory
3. Run docker-compose up --build on any command line or terminal


### Usage

1. The API will be available at: `http://localhost:8080/api/v1/match`

2. Use your preferred API client (e.g., Postman, cURL) to interact with the endpoints.

### API Endpoints

- `GET /api/v1/match/all`: Get all matches.
- `GET /api/v1/match/{id}`: Get a match by id.
- `POST /api/v1/match`: Create a new match.
- `PUT /api/v1/match/{id}`: Update an existing match.
- `DELETE /api/v1/match/{id}`: Delete a match by id.
- `DELETE /api/v1/match/odds`: Delete odds by using a list of ids.
## API Documentation

You can access the API documentation and test the endpoints using Swagger UI:

[Swagger UI] (http://localhost:8080/swagger-ui.html)


### Data Model

The Match object has the following properties:

- `id` (int): Unique identifier of the match.
- `description` (String): Short description of the match.
- `match_date` (Date): Match date in dd/MM/yyyy format.
- `match_time` (LocalTime): Match time in hh:mm format.
- `teamA` (String): Name of the first team.
- `teamB` (String): Name of the second team.
- `sport` (Enum): Sport type (Football, BasketBall).

The MatchOdds object has the following properties:

- `id` (int): Unique identifier of the match odd.
- `matchId` (int): identifier of the match that matchOdds be long to.
- `specifier` (String): String value can be either 1, 2 or X.
- `odd` (double): Match odd based on the teams playing.

### Error Handling

- When a resource is not found, the API will return a 404 Not Found response.
- For invalid requests or data, the API will return a 400 Bad Request response.
- In other cases, the API will return a 500 Interval Server Error followed by the exception message.