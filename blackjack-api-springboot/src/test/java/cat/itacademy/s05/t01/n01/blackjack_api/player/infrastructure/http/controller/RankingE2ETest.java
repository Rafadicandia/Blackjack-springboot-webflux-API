package cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.http.controller;

import cat.itacademy.s05.t01.n01.blackjack_api.TestcontainersConfiguration;
import cat.itacademy.s05.t01.n01.blackjack_api.player.application.dto.ResponsePlayerDTO;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.Player;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerName;
import cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.persistence.mysql.adapter.PlayerRepositoryAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
class RankingE2ETest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PlayerRepositoryAdapter playerRepository;


    @Test
    void getRanking_ShouldReturnPlayersSortedByWinRateDesc() {

        Player winner = Player.createNew(new PlayerName("Winner"));
        winner.recordWin();

        Player loser = Player.createNew(new PlayerName("Loser"));
        loser.recordLoss();

        Player average = Player.createNew(new PlayerName("Average"));
        average.recordWin();
        average.recordLoss();

        Flux.just(winner, loser, average)
                .flatMap(playerRepository::save)
                .blockLast();

        webTestClient.get().uri("/ranking")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ResponsePlayerDTO.class)
                .value(ranking -> {

                    assertThat(ranking).hasSize(3);

                    assertThat(ranking.get(0).name()).isEqualTo("Winner");
                    assertThat(ranking.get(0).winRate()).isEqualTo(100.0);

                    assertThat(ranking.get(1).name()).isEqualTo("Average");
                    assertThat(ranking.get(1).winRate()).isEqualTo(50.0);

                    assertThat(ranking.get(2).name()).isEqualTo("Loser");
                    assertThat(ranking.get(2).winRate()).isEqualTo(0.0);
                });
    }
}
