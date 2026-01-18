package cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.persistence.mysql.mapper;

import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.Player;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerId;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerName;
import cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.persistence.mysql.entity.PlayerEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class PlayerEntityMapper {

    public PlayerEntity toEntity(Player player) {
        return PlayerEntity.builder()
                .id(player.getId().value().toString())
                .name(player.getName().value())
                .wins(player.getTotalWins())
                .losses(player.getTotalLosses())
                .totalGames(player.getTotalGames())
                .winRate(player.getWinRate())
                .balance(player.getBalance())
                .createdAt(player.getCreatedAt().toString())
                .updatedAt(player.getUpdatedAt().toString())
                .build();
    }

    public Player toDomain(PlayerEntity entity) {

        return Player.reconstitute(
                new PlayerId(UUID.fromString(entity.getId())),
                new PlayerName(entity.getName()),
                entity.getWins(),
                entity.getLosses(),
                entity.getTotalGames(),
                entity.getWinRate(),
                LocalDateTime.parse(entity.getCreatedAt()),
                LocalDateTime.parse(entity.getUpdatedAt()),
                entity.getBalance()
        );
    }
}
