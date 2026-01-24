package cat.itacademy.s05.t01.n01.blackjack_api.player.application.usecase;

import cat.itacademy.s05.t01.n01.blackjack_api.player.application.dto.ResponsePlayerDTO;
import cat.itacademy.s05.t01.n01.blackjack_api.player.application.mapper.PlayerMapper;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.Player;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class GetPlayerRankingUseCase {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    public Flux<ResponsePlayerDTO> execute() {
        return playerRepository.findAll()
                .sort(Comparator.comparingDouble(Player::getWinRate).reversed())
                .map(playerMapper::toResponse);
    }
}
