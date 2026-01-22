package cat.itacademy.s05.t01.n01.blackjack_api.game.infrastructure.persistence.mongodb.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardEntity {
    private String rank;
    private String suit;
}
