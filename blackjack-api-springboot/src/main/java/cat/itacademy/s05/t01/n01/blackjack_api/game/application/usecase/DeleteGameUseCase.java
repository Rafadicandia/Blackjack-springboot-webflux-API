package cat.itacademy.s05.t01.n01.blackjack_api.game.application.usecase;

import cat.itacademy.s05.t01.n01.blackjack_api.game.application.exception.GameNotFoundException;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.valueobject.GameId;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteGameUseCase {

    private final GameRepository gameRepository;

    public Mono<Void> execute(String gameId) {
        return gameRepository.findById(GameId.fromString(gameId))
                .switchIfEmpty(Mono.error(new GameNotFoundException(gameId)))
                .flatMap(game -> gameRepository.delete(game.getId()))
                .doOnSuccess(v -> log.info("Game deleted successfully: {}", gameId))
                .doOnError(error -> log.error("Error deleting the game: {}", gameId, error.getMessage()));

    }

}
