package cat.itacademy.s05.t01.n01.blackjack_api.player.application.dto;

import jakarta.validation.constraints.NotBlank;

public record RequestPlayerDTO(
        @NotBlank(message="Name is required") String name
) {
}
