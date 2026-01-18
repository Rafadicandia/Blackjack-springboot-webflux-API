package cat.itacademy.s05.t01.n01.blackjack_api.player.application.usecase;

import cat.itacademy.s05.t01.n01.blackjack_api.player.application.dto.ResponsePlayerDTO;
import cat.itacademy.s05.t01.n01.blackjack_api.player.application.exception.PlayerIdDoesNotExistsInDataBaseException;
import cat.itacademy.s05.t01.n01.blackjack_api.player.application.mapper.PlayerMapper;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerId;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerName;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class UpdatePlayerNameUseCase {

    private final PlayerRepository playerRepository;
    private final PlayerMapper mapper;


    public Mono<ResponsePlayerDTO> execute(PlayerId id, PlayerName name) {
        return playerRepository.findById(id)
                .switchIfEmpty(Mono.error(new PlayerIdDoesNotExistsInDataBaseException("Player with id " + id + " does not exists in data base")))
                .map(player -> {
                    player.changeName(name);
                    return player;
                })
                .flatMap(playerRepository::save)
                .map(mapper::toResponse);
    }

}
