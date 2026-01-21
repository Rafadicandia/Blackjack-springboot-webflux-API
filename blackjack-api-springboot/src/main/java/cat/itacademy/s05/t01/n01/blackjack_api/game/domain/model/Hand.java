package cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hand {
    private final List<Card> cards;

    private Hand(List<Card> cards) {
        this.cards = Collections.unmodifiableList(cards);
    }

    public static Hand empty() {
        return new Hand(new ArrayList<>());
    }

    public Hand addCard(Card card) {
        List<Card> newCards = new ArrayList<>(this.cards);
        newCards.add(card);
        return new Hand(newCards);
    }

    public int calculateScore() {
        int score = 0;
        int aceCount = 0;

        for (Card card : cards) {
            score += card.rank().getValue();
            if (card.rank() == Rank.ACE) {
                aceCount++;
            }
        }

        while (score > 21 && aceCount > 0) {
            score -= 10;
            aceCount--;
        }

        return score;
    }

    public boolean isBlackjack() {
        return cards.size() == 2 && calculateScore() == 21;
    }

    public boolean isBusted() {
        return calculateScore() > 21;
    }

    public boolean shouldDealerHit() {
        return calculateScore() < 17;
    }
    
    public List<Card> getCards() {
        return cards;
    }
}
