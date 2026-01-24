package cat.itacademy.s05.t01.n01.blackjack_api.player.infrastructure.http.controller;

import cat.itacademy.s05.t01.n01.blackjack_api.player.application.dto.ResponsePlayerDTO;
import cat.itacademy.s05.t01.n01.blackjack_api.player.application.usecase.GetPlayerRankingUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class RankingController {

    private final GetPlayerRankingUseCase getPlayerRankingUseCase;

    @GetMapping
    public Flux<ResponsePlayerDTO> getRanking() {
        return getPlayerRankingUseCase.execute();
    }
}
