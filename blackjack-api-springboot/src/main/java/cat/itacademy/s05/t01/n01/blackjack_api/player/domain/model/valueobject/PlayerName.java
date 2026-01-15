package cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject;

import java.util.Objects;

public record PlayerName(String value) {

    public PlayerName {
        Objects.requireNonNull(value, "Player name cannot be null");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerName that = (PlayerName) o;
        return Objects.equals(value.toLowerCase(), that.value.toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value.toLowerCase());
    }

    @Override
    public String toString() {
        return value;
    }
}
