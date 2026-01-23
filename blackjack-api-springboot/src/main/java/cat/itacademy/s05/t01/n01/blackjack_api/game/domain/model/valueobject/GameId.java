package cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.valueobject;

import java.io.Serializable;
import java.util.UUID;

public record GameId(UUID value) {
    public GameId {
        if (value == null) {
            throw new IllegalArgumentException("Game ID cannot be null");
        }
    }

    public static GameId create() {
        return new GameId(UUID.randomUUID());
    }
    
    public static GameId generate() {
        return create();
    }
    
    public static GameId fromString(String id) {
        return new GameId(UUID.fromString(id));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
