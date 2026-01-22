package cat.itacademy.s05.t01.n01.blackjack_api.game.infrastructure.persistence.mongodb.mapper;

import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.*;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.Game;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.valueobject.GameId;
import cat.itacademy.s05.t01.n01.blackjack_api.game.infrastructure.persistence.mongodb.entity.CardEntity;
import cat.itacademy.s05.t01.n01.blackjack_api.game.infrastructure.persistence.mongodb.entity.GameEntity;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerId;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerName;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class GameEntityMapper {

    public GameEntity toEntity(Game game) {
        return GameEntity.builder()
                .gameId(game.getId().value().toString())
                .playerId(game.getPlayerId().value().toString())
                .playerName(game.getPlayerName().value())
                .playerHand(mapHandToEntity(game.getPlayerHand()))
                .dealerHand(mapHandToEntity(game.getDealerHand()))
                .status(game.getStatus().name())
                .build();
    }

    public Game toDomain(GameEntity entity) {
        return new Game(
                new GameId(UUID.fromString(entity.getGameId())),
                new PlayerId(UUID.fromString(entity.getPlayerId())),
                new PlayerName(entity.getPlayerName()),
                mapEntityToHand(entity.getPlayerHand()),
                mapEntityToHand(entity.getDealerHand()),
                GameStatus.valueOf(entity.getStatus())
        );
    }

    private List<CardEntity> mapHandToEntity(Hand hand) {
        if (hand == null || hand.getCards() == null) {
            return List.of();
        }
        return hand.getCards().stream()
                .map(card -> new CardEntity(
                        card.rank().name(),
                        card.suit().name()
                ))
                .collect(Collectors.toList());
    }

    private Hand mapEntityToHand(List<CardEntity> cardEntities) {
        if (cardEntities == null) {
            return new Hand(); // Retorna mano vacía si es null
        }
        List<Card> cards = cardEntities.stream()
                .map(entity -> new Card(
                        Rank.valueOf(entity.getRank()),
                        Suit.valueOf(entity.getSuit())
                ))
                .collect(Collectors.toList());
        return new Hand(cards);
    }
}
