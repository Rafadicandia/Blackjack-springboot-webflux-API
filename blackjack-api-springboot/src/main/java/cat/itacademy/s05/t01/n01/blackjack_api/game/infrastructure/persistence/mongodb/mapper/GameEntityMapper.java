package cat.itacademy.s05.t01.n01.blackjack_api.game.infrastructure.persistence.mongodb.mapper;

import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.*;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.Game;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.valueobject.GameId;
import cat.itacademy.s05.t01.n01.blackjack_api.game.infrastructure.persistence.mongodb.entity.CardEntity;
import cat.itacademy.s05.t01.n01.blackjack_api.game.infrastructure.persistence.mongodb.entity.DeckEntity;
import cat.itacademy.s05.t01.n01.blackjack_api.game.infrastructure.persistence.mongodb.entity.GameEntity;
import cat.itacademy.s05.t01.n01.blackjack_api.game.infrastructure.persistence.mongodb.entity.HandEntity;
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
                .deck(mapDeckToEntity(game.getDeck()))
                .status(game.getStatus().name())
                .createdAt(game.getCreatedAt())
                .updatedAt(game.getUpdatedAt())
                .build();
    }

    private DeckEntity mapDeckToEntity(Deck deck) {
        List<CardEntity> cards = deck.getCards().stream()
                .map(card -> new CardEntity(
                        card.rank().name(),
                        card.suit().name()
                ))
                .collect(Collectors.toList());
        return new DeckEntity(cards);

    }

    private Deck deckEntityToDeck(DeckEntity deck) {

        


    }

    public Game toDomain(GameEntity entity) {
        return Game.reconstitute(
                new GameId(UUID.fromString(entity.getGameId())),
                new PlayerId(UUID.fromString(entity.getPlayerId())),
                new PlayerName(entity.getPlayerName()),
                mapEntityToHand(entity.getPlayerHand()),
                mapEntityToHand(entity.getDealerHand()),
                deckEntityToDeck(entity.getDeck()),
                GameStatus.valueOf(entity.getStatus()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()


        );
    }



    private HandEntity mapHandToEntity(Hand hand) {
        List<CardEntity> cards = hand.getCards().stream()
                .map(card -> new CardEntity(
                        card.rank().name(),
                        card.suit().name()
                ))
                .collect(Collectors.toList());
        return new HandEntity(cards);
    }

    private Hand mapEntityToHand(HandEntity handEntity) {
        List<Card> cards = handEntity.getCards().stream()
                .map(entity -> new Card(
                        Rank.valueOf(entity.getRank()),
                        Suit.valueOf(entity.getSuit())
                ))
                .collect(Collectors.toList());
        return Hand.from(cards);
    }
}
