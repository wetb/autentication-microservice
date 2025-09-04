package co.com.bancolombia.usecase.user;

import co.com.bancolombia.model.exceptions.DomainException;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.Set;

@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;


    public Mono<User> saveUser(User user){
        return userRepository.existsByEmail(user.getEmail())
                        .flatMap(exists -> exists
                        ? Mono.error(new DomainException("email not available", Set.of("email")))
                                : Mono.just(user))
                                .flatMap(userRepository::saveUser);
    }

    public  Mono<Boolean> UserExistsByIdentityDocument(String documentId){
        return userRepository.existsByIdentityDocument(documentId);
    }

}
