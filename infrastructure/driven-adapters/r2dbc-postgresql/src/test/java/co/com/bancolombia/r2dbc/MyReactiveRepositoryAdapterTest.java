package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.user.User;
import co.com.bancolombia.r2dbc.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.reactivestreams.Publisher;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MyReactiveRepositoryAdapterTest {
    // TODO: change four you own tests

    @InjectMocks
    MyReactiveRepositoryAdapter repositoryAdapter;

    @Mock
    MyReactiveRepository repository;

    @Mock
    ObjectMapper mapper;

    @Mock
    TransactionalOperator transactionalOperator;

    @BeforeEach
    void setup() {
        when(transactionalOperator.transactional(any(Mono.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }


    @Test
    void test_whenSaveUser_thenSuccessful() {
        User user = User.builder().build();
        UserEntity userEntity = UserEntity.builder().build();

        when(mapper.map(user, UserEntity.class)).thenReturn(userEntity);
        when(repository.save(userEntity)).thenReturn(Mono.just(userEntity));
        when(mapper.map(userEntity, User.class)).thenReturn(user);

        Mono<User> result = repositoryAdapter.saveUser(user);

        StepVerifier.create(result)
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void test_whenUserExistsByEmail_thenReturnTrue() {
        String email = "test@example.com";
        when(repository.existsByEmail(email)).thenReturn(Mono.just(true));

        StepVerifier.create(repositoryAdapter.existsByEmail(email))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void test_createUser_appliesTransactionAndSavesUser() {
        User user = User.builder().build();
        UserEntity userEntity = UserEntity.builder().build();

        when(mapper.map(user, UserEntity.class)).thenReturn(userEntity);
        when(repository.save(userEntity)).thenReturn(Mono.just(userEntity));
        when(mapper.map(userEntity, User.class)).thenReturn(user);

        // Mock transactional operator to just return the Mono as is
        TransactionalOperator transactionalOperator = Mockito.mock(TransactionalOperator.class);
        //noinspection unchecked
        when(transactionalOperator.transactional(Mockito.any(Mono.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        MyReactiveRepositoryAdapter adapter = new MyReactiveRepositoryAdapter(repository, mapper, transactionalOperator);

        StepVerifier.create(adapter.saveUser(user))
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void test_whenUserDoesNotExistByEmail_thenReturnFalse() {
        String email = "notfound@example.com";
        when(repository.existsByEmail(email)).thenReturn(Mono.just(false));

        StepVerifier.create(repositoryAdapter.existsByEmail(email))
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    void test_whenSaveUser_thenRepositoryThrowsError() {
        User user = User.builder().build();
        UserEntity userEntity = UserEntity.builder().build();

        when(mapper.map(user, UserEntity.class)).thenReturn(userEntity);
        when(repository.save(userEntity)).thenReturn(Mono.error(new RuntimeException("DB error")));

        Mono<User> result = repositoryAdapter.saveUser(user);
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("DB error"))
                .verify();
    }
}
