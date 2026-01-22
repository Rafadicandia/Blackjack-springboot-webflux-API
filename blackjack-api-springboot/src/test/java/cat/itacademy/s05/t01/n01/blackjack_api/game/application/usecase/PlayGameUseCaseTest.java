package cat.itacademy.s05.t01.n01.blackjack_api.game.application.usecase;

import cat.itacademy.s05.t01.n01.blackjack_api.game.application.dto.GameAction;
import cat.itacademy.s05.t01.n01.blackjack_api.game.application.dto.GameResponseDTO;
import cat.itacademy.s05.t01.n01.blackjack_api.game.application.dto.PlayGameRequestDTO;
import cat.itacademy.s05.t01.n01.blackjack_api.game.application.mapper.GameMapper;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.Game;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.GameStatus;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.valueobject.GameId;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.repository.GameRepository;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.Player;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerId;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerName;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.lang.reflect.Field;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayGameUseCaseTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private GameMapper gameMapper;

    @InjectMocks
    private PlayGameUseCase playGameUseCase;

    private Game game;
    private Player player;
    private GameId gameId;
    private PlayerId playerId;

    @BeforeEach
    void setUp() throws Exception {
        playerId = PlayerId.create();
        // Creamos el juego. Puede salir con BJ, así que forzaremos el estado después.
        game = Game.createNew(playerId, new PlayerName("TestPlayer"));
        
        // Forzar estado IN_PROGRESS para asegurar que los tests no fallen por un BJ inicial aleatorio
        Field statusField = Game.class.getDeclaredField("status");
        statusField.setAccessible(true);
        statusField.set(game, GameStatus.IN_PROGRESS);

        gameId = game.getId();
        player = Player.createNew(new PlayerName("TestPlayer"));
        
        // Asignamos el ID al jugador mockeado para que coincida
        Field idField = Player.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(player, playerId);
    }

    @Test
    void shouldProcessHitActionAndContinueGame() {
        // Arrange
        PlayGameRequestDTO request = new PlayGameRequestDTO(GameAction.HIT);
        GameResponseDTO responseDTO = new GameResponseDTO(
                gameId.toString(), playerId.toString(), "TestPlayer", 
                List.of(), List.of(), GameStatus.IN_PROGRESS
        );

        when(gameRepository.findById(any(GameId.class))).thenReturn(Mono.just(game));
        when(gameRepository.save(any(Game.class))).thenReturn(Mono.just(game));
        when(gameMapper.toDTO(any(Game.class))).thenReturn(responseDTO);

        // Act
        Mono<GameResponseDTO> result = playGameUseCase.execute(gameId.toString(), request);

        // Assert
        StepVerifier.create(result)
                .expectNext(responseDTO)
                .verifyComplete();

        verify(gameRepository).save(any(Game.class));
        verify(playerRepository, never()).save(any(Player.class)); 
    }

    @Test
    void shouldProcessStandActionAndFinishGame() {
        // Arrange
        PlayGameRequestDTO request = new PlayGameRequestDTO(GameAction.STAND);
        // El resultado del stand dependerá de las cartas, pero sabemos que NO será IN_PROGRESS
        // Mockeamos la respuesta del mapper para que coincida con lo que esperamos en el assert
        // independientemente del resultado real del juego (Win/Loss/Tie)
        GameResponseDTO responseDTO = new GameResponseDTO(
                gameId.toString(), playerId.toString(), "TestPlayer",
                List.of(), List.of(), GameStatus.PLAYER_WINS 
        );

        when(gameRepository.findById(any(GameId.class))).thenReturn(Mono.just(game));
        when(gameRepository.save(any(Game.class))).thenReturn(Mono.just(game));
        when(playerRepository.findById(any(PlayerId.class))).thenReturn(Mono.just(player));
        when(playerRepository.save(any(Player.class))).thenReturn(Mono.just(player));
        when(gameMapper.toDTO(any(Game.class))).thenReturn(responseDTO);

        // Act
        Mono<GameResponseDTO> result = playGameUseCase.execute(gameId.toString(), request);

        // Assert
        StepVerifier.create(result)
                .expectNext(responseDTO)
                .verifyComplete();

        verify(gameRepository, atLeastOnce()).save(any(Game.class));
        verify(playerRepository).save(any(Player.class));
    }
}
