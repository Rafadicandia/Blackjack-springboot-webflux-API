# Blackjack Reactive API ♠️♥️

A high-performance, non-blocking REST API for a Blackjack game, built with **Java 21** and **Spring Boot WebFlux**.

This project demonstrates a fully reactive stack using **MongoDB** for game state management and **MySQL (R2DBC)** for player profiles and statistics, following **Hexagonal Architecture** (Ports and Adapters) principles.

---

## 🚀 Tech Stack

*   **Language:** Java 21
*   **Framework:** Spring Boot 3 (WebFlux)
*   **Databases:**
    *   **MongoDB** (Reactive): Stores active game sessions and history.
    *   **MySQL** (R2DBC): Stores player profiles, balances, and rankings.
*   **Documentation:** OpenAPI 3 (Swagger UI)
*   **Containerization:** Docker & Docker Compose
*   **Testing:** JUnit 5, Mockito, Testcontainers (Integration/E2E)

---

## 🛠️ Architecture

The project follows **Hexagonal Architecture** to decouple the core domain logic from external dependencies.

*   **Domain:** Contains the core business logic (Entities like `Game`, `Player`, `Hand`) and Repository interfaces. No framework dependencies here.
*   **Application:** Contains Use Cases (Services) that orchestrate the domain logic.
*   **Infrastructure:** Contains the implementation of adapters (REST Controllers, Database Persistence, Configuration).

---

## 🐳 Getting Started (Docker)

The easiest way to run the application is using Docker Compose. This will set up the API, MongoDB, and MySQL containers automatically.

### Prerequisites
*   Docker & Docker Compose installed.

### Run the Application

1.  Clone the repository.
2.  Create a `.env` file in the project root (you can copy `.env.example`).
3.  Navigate to the project root.
4.  Run the following command:

```bash
docker-compose up --build
```

This command will:
1.  Compile the application (using a multi-stage Dockerfile).
2.  Start MongoDB and MySQL containers.
3.  Initialize the MySQL database schema automatically.
4.  Start the Blackjack API on port `8080`.

---

## 📖 API Documentation

Once the application is running, you can access the interactive API documentation (Swagger UI) at:

👉 **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

---

## 🎮 How to Play (Endpoints)

Here is a quick guide to the main flow of the game:

### 1. Create a Game
Start a new game for a player. If the player doesn't exist, they will be created.

*   **POST** `/game/new`
*   **Body:** `{"playerName": "Rafael"}`
*   **Returns:** `gameId`, `playerId`, and initial cards.

### 2. Play (Hit or Stand)
Make a move in an active game.

*   **POST** `/game/{gameId}/play`
*   **Body:**
    *   To Hit: `{"action": "HIT"}`
    *   To Stand: `{"action": "STAND"}`

### 3. Get Ranking
See the leaderboard sorted by win rate.

*   **GET** `/ranking`

### 4. Game Details
Retrieve the state of a specific game.

*   **GET** `/game/{gameId}`

---

## ⚙️ Configuration

The application uses a `.env` file (loaded by Docker Compose) and `application.properties` for configuration.

### Environment Variables (`.env`)
Create a `.env` file in the root directory with the following variables. You can use the example values below.

```properties
# Example .env file
MYSQL_ROOT_PASSWORD=your_secret_password
MYSQL_DATABASE=blackjack
MYSQL_USER=your_user
MYSQL_PASSWORD=your_secret_password
MONGODB_DATABASE=blackjack
```

---

## 🧪 Testing

The project includes Unit Tests and Integration/E2E Tests using **Testcontainers** to ensure database interactions work correctly in a real environment.

To run tests locally (requires Docker):

```bash
./mvnw test
```

---

## 📦 Project Structure

```
src/main/java/cat/itacademy/s05/t01/n01/blackjack_api
├── common          # Shared configuration (Swagger, Global Exceptions)
├── game            # Game Bounded Context
│   ├── application # Use Cases & DTOs
│   ├── domain      # Game Logic (Aggregates, Value Objects)
│   └── infrastructure # Controllers & MongoDB Persistence
└── player          # Player Bounded Context
    ├── application # Use Cases & DTOs
    ├── domain      # Player Logic
    └── infrastructure # Controllers & MySQL Persistence
```
