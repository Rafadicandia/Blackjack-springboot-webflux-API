package cat.itacademy.s05.t01.n01.blackjack_api.player.domain.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
