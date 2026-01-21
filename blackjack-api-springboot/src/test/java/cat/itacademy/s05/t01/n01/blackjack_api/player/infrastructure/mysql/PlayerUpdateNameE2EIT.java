package cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.mysql;

import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.Player;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerId;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerName;
import cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.persistence.mysql.adapter.PlayerRepositoryAdapter;
import cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.persistence.mysql.entity.PlayerEntity;
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
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureWebTestClient
@Testcontainers
public class PlayerUpdateNameE2EIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PlayerRepositoryAdapter repository;

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

        PlayerName initialName = new PlayerName("Original Name");
        Player initialPlayer = Player.createNew(initialName);


        Player savedPlayer = repository.save(initialPlayer).block();


        PlayerId realId = savedPlayer.getId();
        String newName = "Updated Name";


        webTestClient.put()
                .uri("/api/players/{id}/name", realId.value())
                .bodyValue(Map.of("name", newName))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo(newName)
                .jsonPath("$.id").isEqualTo(realId.value().toString());


        StepVerifier.create(repository.findById(savedPlayer.getId()))
                .assertNext(player -> {
                    assertEquals(newName, player.getName().value());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Should return 404 Not Found when player does not exist")
    void shouldReturn404WhenPlayerNotFound() {
        String nonExistentId = UUID.randomUUID().toString();

        webTestClient.put()
                .uri("/api/players/{id}/name", nonExistentId)
                .bodyValue(Map.of("name", "Some Name"))
                .exchange()
                .expectStatus().isNotFound();
    }
}
