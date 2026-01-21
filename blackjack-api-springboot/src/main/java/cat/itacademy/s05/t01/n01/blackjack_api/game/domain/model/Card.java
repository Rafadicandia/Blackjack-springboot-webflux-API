package cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model;

public record Card(Rank rank, Suit suit) {
    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
