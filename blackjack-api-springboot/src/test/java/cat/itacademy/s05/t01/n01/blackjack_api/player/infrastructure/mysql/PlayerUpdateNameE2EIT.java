package cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.mysql;

import cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.persistence.mysql.entity.PlayerEntity;
import cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.persistence.mysql.repository.MySqlPlayerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.data.mongodb.uri=mongodb://localhost:27017/unused", // Evita que busque el default
                "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration"
        }
)
@AutoConfigureWebTestClient
@Testcontainers
public class PlayerUpdateNameE2EIT {


    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private MySqlPlayerRepository repository;

    @Container
    static MySQLContainer mysql = new MySQLContainer("mysql:8.0")
            .withInitScript("schema.sql");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () ->
                String.format("r2dbc:mysql://%s:%d/%s",
                        mysql.getHost(), mysql.getFirstMappedPort(), mysql.getDatabaseName()));
        registry.add("spring.r2dbc.username", mysql::getUsername);
        registry.add("spring.r2dbc.password", mysql::getPassword);
    }

    @Test
    @DisplayName("Should update player name successfully via HTTP API")
    void shouldUpdatePlayerName() {

        String playerId = UUID.randomUUID().toString();
        PlayerEntity initialPlayer = PlayerEntity.builder()
                .id(playerId)
                .name("Original Name")
                .balance(BigDecimal.valueOf(100))
                .wins(0).losses(0).totalGames(0).winRate(0.0)
                .totalGames(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build()
                .asNew(); // Usamos tu método asNew() que ya funciona

        repository.save(initialPlayer).block();

        String newName = "Updated Name";

        webTestClient.put()
                .uri("/api/players/{id}/name", playerId)
                .bodyValue(Map.of("name", newName)) // Asumiendo un DTO con el campo name
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo(newName)
                .jsonPath("$.id").isEqualTo(playerId);

        
        StepVerifier.create(repository.findById(playerId))
                .assertNext(entity -> {
                    assertEquals(newName, entity.getName());
                })
                .verifyComplete();
    }
}

