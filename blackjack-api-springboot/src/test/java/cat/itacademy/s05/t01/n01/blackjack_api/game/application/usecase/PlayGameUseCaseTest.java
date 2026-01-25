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

        game = Game.createNew(playerId, new PlayerName("TestPlayer"));

        Field statusField = Game.class.getDeclaredField("status");
        statusField.setAccessible(true);
        statusField.set(game, GameStatus.IN_PROGRESS);

        gameId = game.getId();
        player = Player.createNew(new PlayerName("TestPlayer"));

        Field idField = Player.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(player, playerId);
    }

    @Test
    void shouldProcessHitActionAndContinueGame() {

        PlayGameRequestDTO request = new PlayGameRequestDTO(GameAction.HIT);
        GameResponseDTO responseDTO = new GameResponseDTO(
                gameId.toString(),
                playerId.toString(),
                "TestPlayer",
                List.of(),
                game.getPlayerHand().calculateScore(),
                List.of(),
                game.getDealerHand().calculateScore(),
                GameStatus.IN_PROGRESS
        );

        when(gameRepository.findById(any(GameId.class))).thenReturn(Mono.just(game));
        when(gameRepository.save(any(Game.class))).thenReturn(Mono.just(game));
        when(gameMapper.toDTO(any(Game.class))).thenReturn(responseDTO);

        Mono<GameResponseDTO> result = playGameUseCase.execute(gameId.toString(), request);

        StepVerifier.create(result)
                .expectNext(responseDTO)
                .verifyComplete();

        verify(gameRepository).save(any(Game.class));
        verify(playerRepository, never()).save(any(Player.class)); 
    }

    @Test
    void shouldProcessStandActionAndFinishGame() {

        PlayGameRequestDTO request = new PlayGameRequestDTO(GameAction.STAND);

        GameResponseDTO responseDTO = new GameResponseDTO(
                gameId.toString(),
                playerId.toString(),
                "TestPlayer",
                List.of(),
                game.getPlayerHand().calculateScore(),
                List.of(),
                game.getDealerHand().calculateScore(),
                GameStatus.PLAYER_WINS
        );

        when(gameRepository.findById(any(GameId.class))).thenReturn(Mono.just(game));
        when(gameRepository.save(any(Game.class))).thenReturn(Mono.just(game));
        when(playerRepository.findById(any(PlayerId.class))).thenReturn(Mono.just(player));
        when(playerRepository.save(any(Player.class))).thenReturn(Mono.just(player));
        when(gameMapper.toDTO(any(Game.class))).thenReturn(responseDTO);

        Mono<GameResponseDTO> result = playGameUseCase.execute(gameId.toString(), request);

        StepVerifier.create(result)
                .expectNext(responseDTO)
                .verifyComplete();

        verify(gameRepository, atLeastOnce()).save(any(Game.class));
        verify(playerRepository).save(any(Player.class));
    }
}
