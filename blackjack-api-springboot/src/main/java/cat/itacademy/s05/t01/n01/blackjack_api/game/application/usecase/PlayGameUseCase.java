package cat.itacademy.s05.t01.n01.blackjack_api.game.application.usecase;

import cat.itacademy.s05.t01.n01.blackjack_api.game.application.dto.GameResponseDTO;
import cat.itacademy.s05.t01.n01.blackjack_api.game.application.dto.PlayGameRequestDTO;
import cat.itacademy.s05.t01.n01.blackjack_api.game.application.mapper.GameMapper;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.Game;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.GameStatus;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.valueobject.GameId;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.repository.GameRepository;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PlayGameUseCase {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final GameMapper gameMapper;

    public Mono<GameResponseDTO> execute(String gameId, PlayGameRequestDTO request) {
        return gameRepository.findById(GameId.fromString(gameId))
                .flatMap(game -> {
                    switch (request.action()) {
                        case HIT -> game.playerHit();
                        case STAND -> game.playerStand();
                    }
                    
                    Mono<Game> saveGame = gameRepository.save(game);
                    
                    if (isGameFinished(game.getStatus())) {
                        return updatePlayerStats(game)
                                .then(saveGame);
                    }
                    return saveGame;
                })
                .map(gameMapper::toDTO);
    }

    private boolean isGameFinished(GameStatus status) {
        return status != GameStatus.IN_PROGRESS;
    }

    private Mono<Void> updatePlayerStats(Game game) {
        return playerRepository.findById(game.getPlayerId())
                .map(player -> {
                    switch (game.getStatus()) {
                        case PLAYER_WINS, PLAYER_BLACKJACK, DEALER_BUSTED -> player.recordWin();
                        case DEALER_WINS, PLAYER_BUSTED -> player.recordLoss();
                        case TIE -> player.recordTie();
                        default -> {} // IN_PROGRESS no debería llegar aquí
                    }
                    return player;
                })
                .flatMap(playerRepository::save)
                .then();
    }
}
