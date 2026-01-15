package cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject;

import java.math.BigDecimal;
import java.util.Objects;

public record PlayerBet(BigDecimal amount) {
    public PlayerBet {
        Objects.requireNonNull(amount, "Bet amount cannot be null");
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Bet amount must be positive");
        }
    }
}
