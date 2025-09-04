package co.com.bancolombia.usecase.user;

import co.com.bancolombia.model.exceptions.DomainException;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

public class UserCaseTest {

    @Mock
    private UserRepository userRepository;
    private UserUseCase userUseCase;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        userUseCase = new UserUseCase(userRepository);
    }

    @Test
    void createUserWhenEmailDoesNotExistDatabase() {
        User user = mock(User.class);
        when(user.getEmail()).thenReturn("emailexaple@domain.co");
        when(userRepository.existsByEmail("emailexaple@domain.co")).thenReturn(Mono.just(false));
        when(userRepository.saveUser(user)).thenReturn(Mono.just(user));

        Mono<User> response = userUseCase.saveUser(user);

        StepVerifier.create(response)
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void createUserWhenEmailExistsInDatabase(){
        User user = mock(User.class);
        when(user.getEmail()).thenReturn("emailexaple@domain.co");
        when(userRepository.existsByEmail("emailexaple@domain.co")).thenReturn(Mono.just(true));

        Mono<User> response = userUseCase.saveUser(user);

        StepVerifier.create(response)
                .expectError(DomainException.class)
                .verify();
        verify(userRepository, never()).saveUser(any());
    }

    @Test
    void createUser_shouldPropagateErrorFromRepository() {
        User user = mock(User.class);
        when(user.getEmail()).thenReturn("emailexaple@domain.co");
        when(userRepository.existsByEmail("emailexaple@domain.co"))
                .thenReturn(Mono.error(new RuntimeException("email not available")));

        Mono<User> response = userUseCase.saveUser(user);

        StepVerifier.create(response)
                .expectErrorMatches(e -> e instanceof RuntimeException && e.getMessage().contains("email not available"))
                .verify();

        verify(userRepository, never()).saveUser(any());
    }
}
