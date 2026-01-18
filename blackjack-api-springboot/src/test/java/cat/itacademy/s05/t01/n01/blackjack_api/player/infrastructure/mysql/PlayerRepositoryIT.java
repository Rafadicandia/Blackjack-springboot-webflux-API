package cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.mysql;


import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.Player;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerName;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.repository.PlayerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Testcontainers
class PlayerRepositoryIT {

    @Container
    static
    MySQLContainer mysql = new MySQLContainer("mysql:8.0");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () ->
                String.format("r2dbc:mysql://%s:%d/%s",
                        mysql.getHost(), mysql.getFirstMappedPort(), mysql.getDatabaseName()));
        registry.add("spring.r2dbc.username", mysql::getUsername);
        registry.add("spring.r2dbc.password", mysql::getPassword);
    }

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    @DisplayName("Should save and find player by name")
    void shouldSaveAndFindPlayerByName() {

        Player player = Player.createNew(new PlayerName("TDD_Master"));

        Mono<Player> saved = playerRepository.save(player)
                .then(playerRepository.findByName(new PlayerName("TDD_Master")));

        StepVerifier.create(saved)
                .assertNext(p -> {
                    assertEquals("TDD_Master", p.getName());
                    assertNotNull(p.getId());
                })
                .verifyComplete();
    }

}
