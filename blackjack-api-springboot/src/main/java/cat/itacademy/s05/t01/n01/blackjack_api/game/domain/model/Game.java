package cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model;

import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.valueobject.GameId;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerId;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerName;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Game {
    private final GameId id;
    private final PlayerId playerId;
    private final PlayerName playerName;
    private Hand playerHand;
    private Hand dealerHand;
    private Deck deck;
    private GameStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Game(GameId id, PlayerId playerId, PlayerName playerName,
                 Hand playerHand, Hand dealerHand, Deck deck,
                 GameStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {

        this.id = id;
        this.playerId = playerId;
        this.playerName = playerName;
        this.playerHand = playerHand;
        this.dealerHand = dealerHand;
        this.deck = deck;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Game createNew(PlayerId playerId, PlayerName playerName) {
        GameId id = GameId.generate();
        Deck deck = Deck.createShuffled();
        Hand playerHand = Hand.empty();
        Hand dealerHand = Hand.empty();
        LocalDateTime now = LocalDateTime.now();

        for (int i = 0; i < 2; i++) {
            Deck.DrawResult drawP = deck.drawCard();
            playerHand = playerHand.addCard(drawP.card());
            deck = drawP.remainingDeck();

            Deck.DrawResult drawD = deck.drawCard();
            dealerHand = dealerHand.addCard(drawD.card());
            deck = drawD.remainingDeck();
        }
        GameStatus status = evaluateInitialBlackjack(playerHand, dealerHand);

        return new Game(id, playerId, playerName, playerHand, dealerHand, deck, status, now, now);
    }

    private static GameStatus evaluateInitialBlackjack(Hand player, Hand dealer) {
        if (player.isBlackjack() && dealer.isBlackjack()) return GameStatus.TIE;
        if (player.isBlackjack()) return GameStatus.PLAYER_BLACKJACK;
        if (dealer.isBlackjack()) return GameStatus.DEALER_WINS; // Dealer gana si tiene BJ y el jugador no
        return GameStatus.IN_PROGRESS;
    }

    public void playerHit() {
        validateGameInProgress();

        Deck.DrawResult draw = deck.drawCard();
        playerHand = playerHand.addCard(draw.card());
        deck = draw.remainingDeck();
        updatedAt = LocalDateTime.now();

        if (playerHand.isBusted()) {
            status = GameStatus.PLAYER_BUSTED;
        }
    }

    public void playerStand() {
        validateGameInProgress();


        while (dealerHand.shouldDealerHit()) { 
            Deck.DrawResult draw = deck.drawCard();
            dealerHand = dealerHand.addCard(draw.card());
            deck = draw.remainingDeck();
        }

        updatedAt = LocalDateTime.now();
        determineWinner();
    }

    private void determineWinner() {
        int pScore = playerHand.calculateScore();
        int dScore = dealerHand.calculateScore();

        if (dealerHand.isBusted()) {
            status = GameStatus.DEALER_BUSTED;
        } else if (pScore > dScore) {
            status = GameStatus.PLAYER_WINS;
        } else if (dScore > pScore) {
            status = GameStatus.DEALER_WINS;
        } else {
            status = GameStatus.TIE;
        }
    }

    private void validateGameInProgress() {
        if (!status.equals(GameStatus.IN_PROGRESS)) {
            throw new IllegalStateException("Game is not in progress. Current status: " + status);
        }
    }

    // ... Getters ...
}