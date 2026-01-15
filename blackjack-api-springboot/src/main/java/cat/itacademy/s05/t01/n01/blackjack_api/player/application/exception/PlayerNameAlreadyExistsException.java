package cat.itacademy.s05.t01.n01.blackjack_api.player.application.exception;

public class PlayerNameAlreadyExistsException extends RuntimeException {
    public PlayerNameAlreadyExistsException(String message) {
        super(message);
    }
}
