package cat.itacademy.s05.t01.n01.blackjack_api.player.application.exception;

public class PlayerIdDoesNotExistsInDataBaseException extends RuntimeException {
    public PlayerIdDoesNotExistsInDataBaseException(String message) {
        super(message);
    }
}
