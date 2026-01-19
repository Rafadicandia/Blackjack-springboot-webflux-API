package cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.persistence.mysql.repository;

import cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.persistence.mysql.entity.PlayerEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;


public interface MySqlPlayerRepository extends R2dbcRepository<PlayerEntity, String> {
}
