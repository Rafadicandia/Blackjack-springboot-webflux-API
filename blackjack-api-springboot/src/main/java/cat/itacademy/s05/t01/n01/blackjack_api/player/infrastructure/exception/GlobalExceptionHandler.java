package cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.exception;

import cat.itacademy.s05.t01.n01.blackjack_api.player.application.exception.PlayerIdDoesNotExistsInDataBaseException;
import cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PlayerIdDoesNotExistsInDataBaseException.class)
    public Mono<ResponseEntity<ApiError>> handlePlayerNotFound(
            PlayerIdDoesNotExistsInDataBaseException ex,
            ServerHttpRequest request) {

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getPath().value()
        );

        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(error));
    }

}
