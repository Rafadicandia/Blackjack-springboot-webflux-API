package cat.itacademy.s05.t01.n01.blackjack_api.game.infrastructure.controller;

import cat.itacademy.s05.t01.n01.blackjack_api.game.application.dto.GameResponseDTO;
import cat.itacademy.s05.t01.n01.blackjack_api.game.application.dto.PlayGameRequestDTO;
import cat.itacademy.s05.t01.n01.blackjack_api.game.application.usecase.CreateGameUseCase;
import cat.itacademy.s05.t01.n01.blackjack_api.game.application.usecase.GetGameUseCase;
import cat.itacademy.s05.t01.n01.blackjack_api.game.application.usecase.PlayGameUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    private final CreateGameUseCase createGameUseCase;
    private final GetGameUseCase getGameUseCase;
    private final PlayGameUseCase playGameUseCase;

    @PostMapping("/{id}/play")
    public Mono<ResponseEntity<GameResponseDTO>> playGame(
            @PathVariable String id,
            @RequestBody PlayGameRequestDTO request
    ) {
        return playGameUseCase.execute(id, request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


}
