package cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
public class Deck {
    private final List<Card> cards;

    private Deck(List<Card> cards) {
        this.cards = Collections.unmodifiableList(cards);
    }

    public static Deck createShuffled() {
        List<Card> allCards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                allCards.add(new Card(rank, suit));
            }
        }
        Collections.shuffle(allCards);
        return new Deck(allCards);
    }

    public DrawResult drawCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Deck is empty");
        }
        Card drawnCard = cards.get(0);
        List<Card> remainingCards = new ArrayList<>(cards.subList(1, cards.size()));
        return new DrawResult(drawnCard, new Deck(remainingCards));
    }

    public record DrawResult(Card card, Deck remainingDeck) {
    }

    public static Deck reconstitute(List<Card> cards) {
        Objects.requireNonNull(cards, "Cards cannot be null");
        if (cards.isEmpty() || cards.size() % 52 != 0) {
            throw new IllegalArgumentException(
                    "Deck size must be a multiple of " + 52 + ", got " + cards.size()
            );
        }
        return new Deck(cards);
    }
}
