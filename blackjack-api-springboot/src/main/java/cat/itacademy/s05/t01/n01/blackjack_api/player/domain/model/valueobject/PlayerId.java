package cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject;

import java.util.Objects;
import java.util.UUID;

public record PlayerId(UUID value) {

    public PlayerId {
        Objects.requireNonNull(value, "Player ID cannot be null");
        if (value.toString().isEmpty()) {
            throw new IllegalArgumentException("Player ID cannot be empty");
        };
    }

    public static PlayerId create() {
        return new PlayerId(UUID.randomUUID());
    }
}
