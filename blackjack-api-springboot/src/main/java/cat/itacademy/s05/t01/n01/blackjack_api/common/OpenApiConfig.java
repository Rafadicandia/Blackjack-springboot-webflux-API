package cat.itacademy.s05.t01.n01.blackjack_api.common;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI blackjackOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Blackjack API")
                        .description("API reactiva para juego de Blackjack con Spring WebFlux, MongoDB y MySQL")
                        .version("v1.0.0"));
    }
}
