package cat.itacademy.s05.t01.n01.blackjack_api.player.application.usecase;

import cat.itacademy.s05.t01.n01.blackjack_api.player.application.exception.PlayerNameAlreadyExistsException;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.Player;

import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerName;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class UpdatePlayerNameUseCase {

    @Autowired
    private final PlayerRepository playerRepository;

    public UpdatePlayerNameUseCase(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Mono<Player> updatePlayerName(PlayerName name) {
        return playerRepository.existsByName(name)
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new PlayerNameAlreadyExistsException("Player with name " + name.value() + " already exists."));
                    } else {
                        Player newPlayer = Player.createNew(name);
                        return playerRepository.save(newPlayer);
                    }
                });
    }

}
