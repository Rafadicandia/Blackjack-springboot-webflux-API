package cat.itacademy.s05.t01.n01.blackjack_api.game.application.usecase;

import cat.itacademy.s05.t01.n01.blackjack_api.game.application.dto.CreateGameRequestDTO;
import cat.itacademy.s05.t01.n01.blackjack_api.game.application.dto.GameResponseDTO;
import cat.itacademy.s05.t01.n01.blackjack_api.game.application.mapper.GameMapper;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.Game;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.GameStatus;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.repository.GameRepository;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.Player;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerId;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerName;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateGameUseCaseTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private GameMapper gameMapper;

    @InjectMocks
    private CreateGameUseCase createGameUseCase;

    @Test
    void shouldCreatePlayerAndGameSuccessfully() {
        // Arrange
        String playerNameStr = "NewPlayer";
        CreateGameRequestDTO request = new CreateGameRequestDTO(playerNameStr);
        
        PlayerId playerId = PlayerId.create();
        Player savedPlayer = Player.createNew(new PlayerName(playerNameStr));
        // Asignamos ID simulado
        try {
            java.lang.reflect.Field idField = Player.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(savedPlayer, playerId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Game savedGame = Game.createNew(playerId, new PlayerName(playerNameStr));
        GameResponseDTO expectedResponse = new GameResponseDTO(
                savedGame.getId().toString(),
                playerId.toString(),
                playerNameStr,
                List.of(),
                List.of(),
                GameStatus.IN_PROGRESS
        );

        when(playerRepository.save(any(Player.class))).thenReturn(Mono.just(savedPlayer));
        when(gameRepository.save(any(Game.class))).thenReturn(Mono.just(savedGame));
        when(gameMapper.toDTO(any(Game.class))).thenReturn(expectedResponse);

        // Act
        Mono<GameResponseDTO> result = createGameUseCase.execute(request);

        // Assert
        StepVerifier.create(result)
                .expectNext(expectedResponse)
                .verifyComplete();

        verify(playerRepository).save(any(Player.class));
        verify(gameRepository).save(any(Game.class));
    }
}
