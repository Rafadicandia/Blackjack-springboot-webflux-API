package cat.itacademy.s05.t01.n01.blackjack_api.game.application.mapper;

import cat.itacademy.s05.t01.n01.blackjack_api.game.application.dto.GameResponseDTO;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.Card;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.Game;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GameMapper {

    public GameResponseDTO toDTO(Game game) {
        return new GameResponseDTO(
                game.getId().toString(),
                game.getPlayerId().toString(),
                game.getPlayerName().value(),
                mapHand(game.getPlayerHand().getCards()),
                mapHand(game.getDealerHand().getCards()),
                game.getStatus()
        );
    }

    private List<String> mapHand(List<Card> cards) {
        return cards.stream()
                .map(Card::toString)
                .collect(Collectors.toList());
    }
}
