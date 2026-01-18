package cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.persistence.mysql.adapter;

import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.Player;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerId;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerName;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.repository.PlayerRepository;
import cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.persistence.mysql.mapper.PlayerEntityMapper;
import cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.persistence.mysql.repository.MySqlPlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PlayerRepositoryAdapter implements PlayerRepository {
    private final MySqlPlayerRepository springDataRepository;
    private final PlayerEntityMapper mapper;

    @Override
    public Mono<Player> save(Player player) {
        return null;
    }

    @Override
    public Mono<Player> findById(PlayerId id) {
        return null;
    }

    @Override
    public Mono<Player> findByName(PlayerName name) {
        return null;
    }

    @Override
    public Flux<Player> findAll() {
        return null;
    }

    @Override
    public Mono<Boolean> existsByName(PlayerName name) {
        return null;
    }

    @Override
    public Mono<Boolean> existsById(PlayerId id) {
        return null;
    }

    @Override
    public Mono<Long> count() {
        return null;
    }

    @Override
    public Mono<Void> deleteById(PlayerId id) {
        return null;
    }
}
