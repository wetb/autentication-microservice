package co.com.bancolombia.model.user.gateways;

import co.com.bancolombia.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface UserRepository {
    Mono<User> saveUser(User user);
//    Flux<User> findAll();
//    Mono<User> findById(Long idUser);
//    Mono<User> deleteById(Long idUser);
    Mono<Boolean> existsByEmail(String email);
}
