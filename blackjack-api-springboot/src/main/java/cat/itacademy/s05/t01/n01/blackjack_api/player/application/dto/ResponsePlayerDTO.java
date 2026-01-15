package cat.itacademy.s05.t01.n01.blackjack_api.player.application.dto;

import java.math.BigDecimal;

public record ResponsePlayerDTO(
        String id,
        String name,
        int totalWins,
        int totalLosses,
        int totalGames,
        double winRate,
        BigDecimal balance
) {
}
