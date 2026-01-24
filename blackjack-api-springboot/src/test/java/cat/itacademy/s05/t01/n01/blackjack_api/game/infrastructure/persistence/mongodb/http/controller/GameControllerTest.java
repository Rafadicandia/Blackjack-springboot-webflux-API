package cat.itacademy.s05.t01.n01.blackjack_api.game.infrastructure.persistence.mongodb.http.controller;

import cat.itacademy.s05.t01.n01.blackjack_api.TestcontainersConfiguration;
import cat.itacademy.s05.t01.n01.blackjack_api.game.application.dto.GameAction;
import cat.itacademy.s05.t01.n01.blackjack_api.game.application.dto.GameResponseDTO;
import cat.itacademy.s05.t01.n01.blackjack_api.game.application.dto.PlayGameRequestDTO;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.Game;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.repository.GameRepository;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.Player;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerId;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerName;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
class GameControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void playGame_whenGameExistsAndActionIsValid_returnsOk() {

        PlayerName playerName = new PlayerName("Joselito");
        Player player = Player.createNew(playerName);
        playerRepository.save(player).block();
        PlayerId playerId = player.getId();

        Game game = Game.createNew(playerId, playerName);

        gameRepository.save(game).block();

        String gameId = game.getId().value().toString();

        PlayGameRequestDTO request = new PlayGameRequestDTO(GameAction.HIT);

        webTestClient.post().uri("/game/{id}/play", gameId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GameResponseDTO.class)
                .value(response -> {
                    assertThat(response.gameId()).isEqualTo(gameId);
                    assertThat(response.playerId()).isEqualTo(playerId.value().toString());

                    assertThat(response.playerHand()).isNotNull();
                    assertThat(response.status()).isNotNull();
                });
    }

    @Test
    void playGame_whenGameNotFound_returnsNotFound() {
        String nonExistentGameId = UUID.randomUUID().toString();
        PlayGameRequestDTO request = new PlayGameRequestDTO(GameAction.STAND);

        webTestClient.post().uri("/game/{id}/play", nonExistentGameId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void shouldReturnGameDetailsWhenGameExists_shouldReturnOk() {
        // Arrange
        PlayerName playerName = new PlayerName("TestPlayer");
        Player player = Player.createNew(playerName);
        playerRepository.save(player).block();
        PlayerId playerId = player.getId();

        Game game = Game.createNew(playerId, playerName);
        gameRepository.save(game).block();
        String gameId = game.getId().value().toString();

        // Act
        webTestClient.get().uri("/game/{id}", gameId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GameResponseDTO.class)
                .value(response -> {
                    assertThat(response.gameId()).isEqualTo(gameId);
                    assertThat(response.playerId()).isEqualTo(playerId.value().toString());
                    assertThat(response.playerHand()).isNotNull();
                    assertThat(response.status()).isNotNull();
                });

    }

    //Mètode: GET
    //
    //Descripció: Obté els detalls d'una partida específica de Blackjack.
    //
    //Endpoint: /game/{id}
    //
    //Paràmetres d'entrada: Identificador únic de la partida (id)
    //
    //Resposta exitosa: Codi 200 OK amb informació detallada sobre la partida.


}



