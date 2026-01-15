package cat.itacademy.s05.t01.n01.blackjack_api.player.domain.repository;

import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.Player;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerId;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerName;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlayerRepository {
    Mono<Player> save(Player player);

    Mono<Player> findById(PlayerId id);

    Mono<Player> findByName(PlayerName name);

    Flux<Player> findAll();

    Mono<Boolean> existsByName(PlayerName name);

    Mono<Boolean> existsById(PlayerId id);

    Mono<Long> count();

    Mono<Void> deleteById(PlayerId id);
}
