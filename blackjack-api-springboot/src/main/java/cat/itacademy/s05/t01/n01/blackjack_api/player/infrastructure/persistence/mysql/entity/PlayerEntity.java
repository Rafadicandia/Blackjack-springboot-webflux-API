package cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.persistence.mysql.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("players")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerEntity {

    @Id
    private String id;

    @Column("name")
    private String name;

    @Column("wins")
    private Integer wins;

    @Column("losses")
    private Integer losses;

    @Column("total_games")
    private Integer totalGames;

    @Column("win_rate")
    private Double winRate;

    @Column("balance")
    private BigDecimal balance;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Column("deleted_at")
    private LocalDateTime deletedAt;

}
