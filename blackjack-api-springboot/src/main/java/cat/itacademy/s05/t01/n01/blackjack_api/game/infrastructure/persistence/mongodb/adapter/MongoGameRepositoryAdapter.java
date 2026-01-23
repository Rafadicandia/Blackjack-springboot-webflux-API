package cat.itacademy.s05.t01.n01.blackjack_api.game.infrastructure.persistence.mongodb.adapter;

import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.Game;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.model.valueobject.GameId;
import cat.itacademy.s05.t01.n01.blackjack_api.game.domain.repository.GameRepository;
import cat.itacademy.s05.t01.n01.blackjack_api.game.infrastructure.persistence.mongodb.entity.GameEntity;
import cat.itacademy.s05.t01.n01.blackjack_api.game.infrastructure.persistence.mongodb.mapper.GameEntityMapper;
import cat.itacademy.s05.t01.n01.blackjack_api.game.infrastructure.persistence.mongodb.repository.MongoDbRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class MongoGameRepositoryAdapter implements GameRepository {

    private final MongoDbRepository mongoDbRepository;
    private final GameEntityMapper gameEntityMapper;

    public MongoGameRepositoryAdapter(MongoDbRepository mongoDbRepository, GameEntityMapper gameEntityMapper) {
        this.mongoDbRepository = mongoDbRepository;
        this.gameEntityMapper = gameEntityMapper;
    }

    @Override
    public Mono<Game> save(Game game) {
        GameEntity gameEntity = gameEntityMapper.toEntity(game);
        return mongoDbRepository.save(gameEntity)
                .map(gameEntityMapper::toDomain);
    }

    @Override
    public Mono<Game> findById(GameId id) {
        return mongoDbRepository.findById(id.value().toString())
                .map(gameEntityMapper::toDomain);
    }
}
