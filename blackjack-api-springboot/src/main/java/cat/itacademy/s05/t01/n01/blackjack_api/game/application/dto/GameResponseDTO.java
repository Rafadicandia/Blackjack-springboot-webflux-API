package cat.itacademy.s05.t01.n01.blackjack_api.game.application.dto;

import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.GameStatus;

import java.util.List;

public record GameResponseDTO(
        String gameId,
        String playerId,
        String playerName,
        List<String> playerHand,
        int playerScore,
        List<String> dealerHand,
        int dealerScore,
        GameStatus status
) {}
