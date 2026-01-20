package cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.http.controller;

import cat.itacademy.s05.t01.n01.blackjack_api.player.application.dto.RequestPlayerDTO;
import cat.itacademy.s05.t01.n01.blackjack_api.player.application.dto.ResponsePlayerDTO;
import cat.itacademy.s05.t01.n01.blackjack_api.player.application.usecase.UpdatePlayerNameUseCase;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerController {

    private final UpdatePlayerNameUseCase updatePlayerNameUseCase;

    @PutMapping("/{id}/name")
    public Mono<ResponseEntity<ResponsePlayerDTO>> updateName(
            @PathVariable PlayerId id,
            @RequestBody RequestPlayerDTO request) {

        return updatePlayerNameUseCase.execute(id, request)
                .map(player -> ResponseEntity.ok(new ResponsePlayerDTO(
                        player.id(),
                        player.name(),
                        player.totalWins(),
                        player.totalLosses(),
                        player.totalGames(),
                        player.winRate(),
                        player.balance()
                )))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
