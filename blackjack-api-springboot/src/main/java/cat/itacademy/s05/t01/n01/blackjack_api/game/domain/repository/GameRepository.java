package cat.itacademy.s05.t01.n01.blackjack_api.game.domain.repository;

import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.Game;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.valueobject.GameId;
import reactor.core.publisher.Mono;

public interface GameRepository {
    Mono<Game> save(Game game);
    Mono<Game> findById(GameId id);
    Mono<Void> delete(GameId id);
}
