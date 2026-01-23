package cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.mysql;

import cat.itacademy.s05.t01.n01.blackjack_api.TestcontainersConfiguration;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.Player;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerId;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerName;
import cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.persistence.mysql.adapter.PlayerRepositoryAdapter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
public class PlayerUpdateNameE2EIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PlayerRepositoryAdapter repository;

    @Test
    @DisplayName("Should update player name successfully via HTTP API")
    void shouldUpdatePlayerName() {

        PlayerName initialName = new PlayerName("Original Name");
        Player initialPlayer = Player.createNew(initialName);


        Player savedPlayer = repository.save(initialPlayer).block();


        PlayerId realId = savedPlayer.getId();
        String newName = "Updated Name";


        webTestClient.put()
                .uri("/player/{id}", realId.value()) // Corregido según README: /player/{playerId}
                .bodyValue(newName) // Corregido según README: Body es el nuevo nombre directamente, no un JSON
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
                .uri("/player/{id}", nonExistentId)
                .bodyValue("Some Name")
                .exchange()
                .expectStatus().isNotFound();
    }
}
