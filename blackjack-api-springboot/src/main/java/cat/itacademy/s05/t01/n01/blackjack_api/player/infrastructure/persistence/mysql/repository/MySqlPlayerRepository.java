package cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.persistence.mysql.repository;

import cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.persistence.mysql.entity.PlayerEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MySqlPlayerRepository extends ReactiveCrudRepository<PlayerEntity, String> {
}
