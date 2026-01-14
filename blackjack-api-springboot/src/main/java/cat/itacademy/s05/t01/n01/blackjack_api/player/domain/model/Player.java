package cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model;

import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerId;

import java.time.LocalDateTime;
import java.util.Objects;

public class Player {

    private PlayerId id;
    private PlayerName name;
    private int totalWins;
    private int totalLosses;
    private int totalGames;
    private double winRate;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Player(PlayerId id, PlayerName name, int totalWins, int totalLosses,
                   int totalGames, double winRate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "Player name cannot be null");
        this.totalWins = totalWins;
        this.totalLosses = totalLosses;
        this.totalGames = totalGames;
        this.winRate = winRate;
        this.createdAt = Objects.requireNonNull(createdAt, "Created at cannot be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "Updated at cannot be null");
    }

    public static Player createNew(PlayerName name) {
        LocalDateTime now = LocalDateTime.now();
        return new Player(null, name, 0, 0, 0, 0.0, now, now);
    }


    public static Player reconstitute(PlayerId id, PlayerName name, int totalWins, int totalLosses,
                                      int totalGames, double winRate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Player(id, name, totalWins, totalLosses, totalGames, winRate, createdAt, updatedAt);
    }

    public void recordWin() {
        this.totalWins++;
        this.totalGames++;
        updateWinRate();
        this.updatedAt = LocalDateTime.now();
    }

    public void recordLoss() {
        this.totalLosses++;
        this.totalGames++;
        updateWinRate();
        this.updatedAt = LocalDateTime.now();
    }


    public void recordTie() {
        this.totalGames++;
        updateWinRate();
        this.updatedAt = LocalDateTime.now();
    }


    public void changeName(PlayerName newName) {
        this.name = Objects.requireNonNull(newName, "New name cannot be null");
        this.updatedAt = LocalDateTime.now();
    }

    private void updateWinRate() {
        if (totalGames > 0) {
            this.winRate = (double) totalWins / totalGames * 100;
        }
    }


    public void assignId(PlayerId id) {
        if (this.id != null) {
            throw new IllegalStateException("Player ID already assigned");
        }
        this.id = Objects.requireNonNull(id, "Player ID cannot be null");
    }

    public PlayerId getId() {
        return id;
    }

    public PlayerName getName() {
        return name;
    }

    public int getTotalWins() {
        return totalWins;
    }

    public int getTotalLosses() {
        return totalLosses;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public double getWinRate() {
        return winRate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public boolean hasId() {
        return id != null;
    }
}
