package cat.itacademy.s05.t01.n01.blackjack_api.player.application.usecase;

import cat.itacademy.s05.t01.n01.blackjack_api.player.application.dto.ResponsePlayerDTO;
import cat.itacademy.s05.t01.n01.blackjack_api.player.application.exception.PlayerIdDoesNotExistsInDataBaseException;
import cat.itacademy.s05.t01.n01.blackjack_api.player.application.mapper.PlayerMapper;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.Player;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerId;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.model.valueobject.PlayerName;
import cat.itacademy.s05.t01.n01.blackjack_api.player.domain.repository.PlayerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdatePlayerNameUseCaseTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PlayerMapper playerMapper;

    @InjectMocks
    private UpdatePlayerNameUseCase useCase;

    @Test
    void shouldUpdatePlayerNameSuccessfully() {
        PlayerId id = PlayerId.create();
        PlayerName newName = new PlayerName("NewName");
        Player existingPlayer = Player.createNew(new PlayerName("OldName"));

        ResponsePlayerDTO expectedDTO = new ResponsePlayerDTO(id.value().toString(), "NewName", 0, 0, 0, 0.0, null);

        when(playerRepository.findById(id)).thenReturn(Mono.just(existingPlayer));
        when(playerRepository.save(any(Player.class))).thenReturn(Mono.just(existingPlayer));
        when(playerMapper.toResponse(any(Player.class))).thenReturn(expectedDTO);
        Mono<ResponsePlayerDTO> result = useCase.execute(id, newName);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.name().equals("NewName")) // Ojo al typo "NewNam"
                .verifyComplete();

        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    @DisplayName("Must throw error if player does not exist")
    void shouldFailWhenPlayerDoesNotExist() {

        PlayerId id = new PlayerId(UUID.randomUUID());
        PlayerName newName = new PlayerName("TestName");

        when(playerRepository.findById(id)).thenReturn(Mono.empty());

        Mono<ResponsePlayerDTO> result = useCase.execute(id, newName);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof PlayerIdDoesNotExistsInDataBaseException &&
                        throwable.getMessage().equals("Player with id " + id + " does not exists in data base"))
                .verify();

        verify(playerRepository, never()).save(any());
    }

}