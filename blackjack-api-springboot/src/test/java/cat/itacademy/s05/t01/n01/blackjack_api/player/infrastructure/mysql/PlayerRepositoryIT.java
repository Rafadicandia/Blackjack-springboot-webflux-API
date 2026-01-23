package cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.mysql;

import cat.itacademy.s05.t01.n01.blackjack_api.TestcontainersConfiguration;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.Player;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerName;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.repository.PlayerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class PlayerRepositoryIT {

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    @DisplayName("Should save and find player by ID")
    void shouldSaveAndFindPlayerById() {

        Player player = Player.createNew(new PlayerName("TDD_Master"));

        Mono<Player> savedAndFound = playerRepository.save(player)
                .flatMap(savedPlayer -> playerRepository.findById(savedPlayer.getId()));

        StepVerifier.create(savedAndFound)
                .assertNext(p -> {
                    assertEquals("TDD_Master", p.getName().value());
                    assertNotNull(p.getId());
                })
                .verifyComplete();
    }
}
