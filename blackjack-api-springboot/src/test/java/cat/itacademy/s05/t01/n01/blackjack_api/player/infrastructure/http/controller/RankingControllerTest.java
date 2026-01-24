package cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.http.controller;

import cat.itacademy.s05.t01.n01.blackjack_api.player.application.dto.ResponsePlayerDTO;
import cat.itacademy.s05.t01.n01.blackjack_api.player.application.usecase.GetPlayerRankingUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

@WebFluxTest(RankingController.class)
class RankingControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private GetPlayerRankingUseCase getPlayerRankingUseCase;

    @Test
    void getRanking_ShouldReturnPlayersSortedByWinRate() {
        ResponsePlayerDTO player1 = new ResponsePlayerDTO(
                "1", "Player 1", 10, 0, 10, 100.0, BigDecimal.ZERO
        );
        ResponsePlayerDTO player2 = new ResponsePlayerDTO(
                "2", "Player 2", 5, 5, 10, 50.0, BigDecimal.ZERO
        );

        when(getPlayerRankingUseCase.execute()).thenReturn(Flux.just(player1, player2));

        webTestClient.get()
                .uri("/ranking")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ResponsePlayerDTO.class)
                .contains(player1, player2);
    }
}
