package co.com.bancolombia.r2dbc;

import co.com.bancolombia.r2dbc.entity.UserEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.format.number.money.MonetaryAmountFormatter;
import reactor.core.publisher.Mono;

// TODO: This file is just an example, you should delete or modify it
public interface MyReactiveRepository extends ReactiveCrudRepository<UserEntity, Long>, ReactiveQueryByExampleExecutor<UserEntity> {
    // Mono<Void> deletedByUsrId(Long idUser);
    Mono<Boolean> existsByEmail(String email);
    Mono<Boolean> existsByIdentityDocument (String documentId);

}
