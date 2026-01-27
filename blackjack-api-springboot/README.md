# Blackjack Reactive API ♠️♥️

A high-performance, non-blocking REST API for a Blackjack game, built with **Java 21** and **Spring Boot WebFlux**.

This project demonstrates a fully reactive stack using **MongoDB** for game state management and **MySQL (R2DBC)** for player profiles and statistics, following **Hexagonal Architecture** (Ports and Adapters) principles.

🚀 **Live Demo:** [https://blackjack-api-mrxe.onrender.com/](https://blackjack-api-mrxe.onrender.com/)  

---

## 🚀 Tech Stack

*   **Language:** Java 21
*   **Framework:** Spring Boot 3 (WebFlux)
*   **Databases:**
    *   **MongoDB Atlas** (Cloud): Managed NoSQL database for active game sessions and history.
    *   **MySQL** (freesqldatabase.com): Cloud-hosted SQL database for player profiles, balances, and rankings via R2DBC.
*   **Documentation:** OpenAPI 3 (Swagger UI)
*   **Containerization:** Docker & Docker Compose
*   **Testing:** JUnit 5, Mockito, Testcontainers (Integration/E2E)
*   **Deployment:** Render (Application), MongoDB Atlas (DB), freesqldatabase.com (DB)

---

## 🛠️ Architecture

The project follows **Hexagonal Architecture** to decouple the core domain logic from external dependencies.

*   **Domain:** Contains the core business logic (Entities like `Game`, `Player`, `Hand`) and Repository interfaces. No framework dependencies here.
*   **Application:** Contains Use Cases (Services) that orchestrate the domain logic.
*   **Infrastructure:** Contains the implementation of adapters (REST Controllers, Database Persistence, Configuration).

---

## 🐳 Getting Started (Local Development)

The easiest way to run the application locally is using Docker Compose.

### Prerequisites
*   Docker & Docker Compose installed.

### Run the Application

1.  Clone the repository.
2.  Create a `.env` file in the project root (you can copy `.env.example`).
3.  Run the following command:

```bash
docker-compose up --build
```

This command will:
1.  Compile the application.
2.  Start MongoDB and MySQL containers.
3.  Initialize the database schema.
4.  Start the API on port `8080`.

---

## 🎮 How to Play (Endpoints)

You can test these endpoints using the **Swagger UI** link above or via Postman/cURL.

📖 **Swagger UI:** [https://blackjack-api-mrxe.onrender.com/swagger-ui/index.html](https://blackjack-api-mrxe.onrender.com/swagger-ui/index.html)

### 1. Create a Game
Start a new game for a player.

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

### Environment Variables (`.env`)
For local development, create a `.env` file based on `.env.example`:

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

To run tests locally (requires Docker for Testcontainers):

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
