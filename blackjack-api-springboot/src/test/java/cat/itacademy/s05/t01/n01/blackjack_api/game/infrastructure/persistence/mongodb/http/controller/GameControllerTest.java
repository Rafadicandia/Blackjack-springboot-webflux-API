package cat.itacademy.s05.t01.n01.blackjack_api.game.infrastructure.persistence.mongodb.http.controller;

import cat.itacademy.s05.t01.n01.blackjack_api.TestcontainersConfiguration;
import cat.itacademy.s05.t01.n01.blackjack_api.game.application.dto.GameAction;
import cat.itacademy.s05.t01.n01.blackjack_api.game.application.dto.GameResponseDTO;
import cat.itacademy.s05.t01.n01.blackjack_api.game.application.dto.PlayGameRequestDTO;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.Game;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.valueobject.GameId;
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
import reactor.core.publisher.Mono;

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
        // Limpiar base de datos antes de cada test si es necesario, 
        // aunque Testcontainers suele dar una instancia limpia si se reinicia, 
        // pero en SpringBootTest se suele reutilizar el contexto.
        // Aquí podríamos borrar datos si tuviéramos un método deleteAll, 
        // pero por ahora confiaremos en crear IDs únicos.
    }

    @Test
    void playGame_whenGameExistsAndActionIsValid_returnsOk() {
        // 1. Crear y guardar un Player y un Game reales en la DB
        PlayerName playerName = new PlayerName("Joselito");
        Player player = Player.createNew(playerName);
        playerRepository.save(player).block();
        PlayerId playerId = player.getId();


        Game game = Game.createNew(playerId, new PlayerName("TestPlayer"));
        gameRepository.save(game).block();
        
        String gameId = game.getId().value().toString();

        // 2. Preparar la request
        PlayGameRequestDTO request = new PlayGameRequestDTO(GameAction.HIT);

        // 3. Ejecutar la llamada al endpoint
        webTestClient.post().uri("/game/{id}/play", gameId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GameResponseDTO.class)
                .value(response -> {
                    assertThat(response.gameId()).isEqualTo(gameId);
                    assertThat(response.playerId()).isEqualTo(playerId.value().toString());
                    // Verificar que la mano del jugador ha cambiado (ha recibido una carta)
                    // Al inicio tiene 2 cartas, tras HIT debería tener 3 (si no se pasa de 21 y termina)
                    // O al menos verificar que no es null
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
}
