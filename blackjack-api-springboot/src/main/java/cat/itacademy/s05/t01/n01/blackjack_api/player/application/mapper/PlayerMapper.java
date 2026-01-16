package cat.itacademy.s05.t01.n01.blackjack_api.player.application.mapper;

import cat.itacademy.s05.t01.n01.blackjack_api.player.application.dto.ResponsePlayerDTO;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.Player;
import lombok.Data;
import org.springframework.stereotype.Component;


@Component
public class PlayerMapper {

    public ResponsePlayerDTO toResponse(Player player) {
        return new ResponsePlayerDTO(
                player.getId().value().toString(),
                player.getName().value(),
                player.getTotalWins(),
                player.getTotalLosses(),
                player.getTotalGames(),
                player.getWinRate(),
                player.getBalance()
        );
    }


}
