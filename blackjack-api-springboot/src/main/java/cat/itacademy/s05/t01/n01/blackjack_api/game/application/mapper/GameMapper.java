package cat.itacademy.s05.t01.n01.blackjack_api.game.application.mapper;

import cat.itacademy.s05.t01.n01.blackjack_api.game.application.dto.GameResponseDTO;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.Card;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.Game;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.GameStatus;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.Hand;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GameMapper {

    public GameResponseDTO toDTO(Game game) {
        return new GameResponseDTO(
                game.getId().toString(),
                game.getPlayerId().value().toString(),
                game.getPlayerName().value(),
                playerMapHand(game),
                game.getPlayerHand().calculateScore(),
                dealerMapHand(game),
                dealerMapScore(game),
                game.getStatus()
        );
    }

    private List<String> playerMapHand(Game game) {
        return game.getPlayerHand().getCards().stream()
                .map(Card::toString)
                .collect(Collectors.toList());
    }

    private List<String> dealerMapHand(Game game) {
        if (game.getStatus().equals(GameStatus.IN_PROGRESS)) {
            List<Card> cards = game.getDealerHand().getCards();
            return List.of(cards.getFirst().toString(), "HIDDEN");

        }
        return game.getDealerHand().getCards().stream()
                .map(Card::toString)
                .collect(Collectors.toList());
    }

    private int dealerMapScore(Game game) {

        if (game.getStatus().equals(GameStatus.IN_PROGRESS)) {
            List<Card> cards = game.getDealerHand().getCards();

            return Hand.from(List.of(cards.getFirst())).calculateScore();

        }

        return game.getDealerHand().calculateScore();
    }
}
