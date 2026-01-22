package cat.itacademy.s05.t01.n01.blackjack_api.game.application.usecase;

import cat.itacademy.s05.t01.n01.blackjack_api.game.application.dto.GameResponseDTO;
import cat.itacademy.s05.t01.n01.blackjack_api.game.application.mapper.GameMapper;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.Game;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.GameStatus;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.valueobject.GameId;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.repository.GameRepository;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerId;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetGameUseCaseTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameMapper gameMapper;

    @InjectMocks
    private GetGameUseCase getGameUseCase;

    @Test
    void shouldReturnGameDetailsWhenGameExists() {
        // Arrange
        GameId gameId = GameId.create();
        PlayerId playerId = PlayerId.create();
        Game existingGame = Game.createNew(playerId, new PlayerName("TestPlayer"));
        // Forzar el ID del juego para que coincida con el gameId del test
        try {
            java.lang.reflect.Field idField = Game.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(existingGame, gameId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        GameResponseDTO expectedResponse = new GameResponseDTO(
                gameId.toString(),
                playerId.toString(),
                "TestPlayer",
                List.of(),
                List.of(),
                GameStatus.IN_PROGRESS
        );

        when(gameRepository.findById(any(GameId.class))).thenReturn(Mono.just(existingGame));
        when(gameMapper.toDTO(any(Game.class))).thenReturn(expectedResponse);

        // Act
        Mono<GameResponseDTO> result = getGameUseCase.execute(gameId.toString());

        // Assert
        StepVerifier.create(result)
                .expectNext(expectedResponse)
                .verifyComplete();

        verify(gameRepository).findById(any(GameId.class));
        verify(gameMapper).toDTO(any(Game.class));
    }

    @Test
    void shouldReturnEmptyMonoWhenGameDoesNotExist() {
        // Arrange
        GameId gameId = GameId.create();

        when(gameRepository.findById(any(GameId.class))).thenReturn(Mono.empty());

        // Act
        Mono<GameResponseDTO> result = getGameUseCase.execute(gameId.toString());

        // Assert
        StepVerifier.create(result)
                .expectComplete()
                .verify();

        verify(gameRepository).findById(any(GameId.class));
        verify(gameMapper, never()).toDTO(any(Game.class)); // No se debe llamar al mapper si no hay juego
    }
}
