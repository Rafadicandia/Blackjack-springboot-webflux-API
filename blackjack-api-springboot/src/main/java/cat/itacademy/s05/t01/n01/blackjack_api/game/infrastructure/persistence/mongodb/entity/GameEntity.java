package cat.itacademy.s05.t01.n01.blackjack_api.game.infrastructure.persistence.mongodb.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "games")
public class GameEntity {
    @Id
    private String gameId;
    private String playerId;
    private String playerName;
    private HandEntity playerHand;
    private HandEntity dealerHand;
    private DeckEntity deck;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
