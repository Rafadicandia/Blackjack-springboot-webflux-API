package cat.itacademy.s05.t01.n01.blackjack_api.game.infrastructure.persistence.mongodb.repository;

import cat.itacademy.s05.t01.n01.blackjack_api.game.infrastructure.persistence.mongodb.entity.GameEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MongoDbRepository extends ReactiveMongoRepository<GameEntity, String> {
}
