package cat.itacademy.s05.t01.n01.blackjack_api;

import org.springframework.boot.SpringApplication;

public class TestBlackjackApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(BlackjackApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
