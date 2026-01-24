package cat.itacademy.s05.t01.n01.blackjack_api.player.application.usecase;

import cat.itacademy.s05.t01.n01.blackjack_api.player.application.dto.ResponsePlayerDTO;
import cat.itacademy.s05.t01.n01.blackjack_api.player.application.mapper.PlayerMapper;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.Player;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerName;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPlayerRankingUseCaseTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PlayerMapper playerMapper;

    @InjectMocks
    private GetPlayerRankingUseCase getPlayerRankingUseCase;

    private Player player1;
    private Player player2;
    private ResponsePlayerDTO responsePlayerDTO1;
    private ResponsePlayerDTO responsePlayerDTO2;

    @BeforeEach
    void setUp() {
        player1 = Player.createNew(new PlayerName("Player 1"));
        player1.recordWin(); // 100% win rate

        player2 = Player.createNew(new PlayerName("Player 2"));
        player2.recordLoss(); // 0% win rate

        responsePlayerDTO1 = new ResponsePlayerDTO(
                "1", "Player 1", 1, 0, 1, 100.0, BigDecimal.ZERO
        );
        responsePlayerDTO2 = new ResponsePlayerDTO(
                "2", "Player 2", 0, 1, 1, 0.0, BigDecimal.ZERO
        );
    }

    @Test
    void execute_ShouldReturnPlayersSortedByWinRateDesc() {
        when(playerRepository.findAll()).thenReturn(Flux.just(player2, player1));
        when(playerMapper.toResponse(player1)).thenReturn(responsePlayerDTO1);
        when(playerMapper.toResponse(player2)).thenReturn(responsePlayerDTO2);

        Flux<ResponsePlayerDTO> result = getPlayerRankingUseCase.execute();

        StepVerifier.create(result)
                .expectNext(responsePlayerDTO1)
                .expectNext(responsePlayerDTO2)
                .verifyComplete();
    }
}
