package cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.persistence.mysql.entity;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Table(schema = "players")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerEntity {

    @Id
    private String id;
    private String name;
    private Integer score;
    private Integer wins;
    private Integer losses;
    private Double winRate;
    private Integer totalGames;
    private BigDecimal balance;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;

}
