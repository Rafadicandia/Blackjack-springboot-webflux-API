package cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.config;

import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerId;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.util.UUID;

@Configuration
public class WebConfig implements WebFluxConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToPlayerIdConverter());
    }

    private static class StringToPlayerIdConverter implements Converter<String, PlayerId> {
        @Override
        public PlayerId convert(String source) {
            return new PlayerId(UUID.fromString(source));
        }
    }
}
