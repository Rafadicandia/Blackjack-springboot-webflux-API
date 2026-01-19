package cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.persistence.mysql.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("players")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerEntity implements Persistable<String> {

    @Id
    private String id;
    private String name;
    private Integer score;
    private Integer wins;
    private Integer losses;
    private Double winRate;
    private Integer totalGames;
    private BigDecimal balance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @Transient
    private boolean isNewRecord = false;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return isNewRecord || id == null;
    }

    public PlayerEntity asNew() {
        this.isNewRecord = true;
        return this;
    }
}
