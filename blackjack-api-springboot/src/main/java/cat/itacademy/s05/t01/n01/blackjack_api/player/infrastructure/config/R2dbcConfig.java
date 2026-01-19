package cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EnableR2dbcRepositories(basePackages = "cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.persistence.mysql.repository")
public class R2dbcConfig {
}
