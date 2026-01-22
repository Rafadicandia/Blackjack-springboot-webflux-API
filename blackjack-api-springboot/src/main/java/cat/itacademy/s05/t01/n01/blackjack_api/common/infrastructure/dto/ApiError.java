package cat.itacademy.s05.t01.n01.blackjack_api.common.infrastructure.dto;

import java.time.LocalDateTime;

public record ApiError(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path) {
}
