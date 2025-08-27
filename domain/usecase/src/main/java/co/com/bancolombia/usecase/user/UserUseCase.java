package co.com.bancolombia.usecase.user;

import co.com.bancolombia.model.exceptions.DomainException;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
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

//    public Flux<User> getAllUsers(){
//        return userRepository.findAll();
//    }
//
//    public Mono<User> updateUser(User user){
//        return userRepository.saveUser(user);
//    }
//
//    public Mono<User> getUserById(Long idUser){
//        return userRepository.findById(idUser);
//    }
//
//    public Mono<User> deleteUserById(Long idUser){
//        return  userRepository.deleteById(idUser);
//    }
}
