package cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.persistence.mysql.mapper;

import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.Player;
import cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.persistence.mysql.entity.PlayerEntity;
import org.springframework.stereotype.Component;

@Component
public class PlayerEntityMapper {

    public PlayerEntity playerToEntity(Player player) {
        return new PlayerEntity(
                player.getId().value().toString(),
                player.getName().value(),
                player.getTotalWins(),
                player.getTotalLosses(),
                player.getTotalGames(),
                player.getWinRate(),
                player.getBalance(),
                player.getCreatedAt().toString(),
                player.getUpdatedAt().toString(),
                null
        );

    }
}
