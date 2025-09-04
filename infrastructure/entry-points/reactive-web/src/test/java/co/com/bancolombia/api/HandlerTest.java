package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.request.CreateUserDTO;
import co.com.bancolombia.api.dto.response.UserDTO;
import co.com.bancolombia.api.mapper.UserMapper;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.usecase.user.UserUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@WebFluxTest(Handler.class)
public class HandlerTest {

    @Autowired
    private WebTestClient webTestClient;
    private UserMapper userMapper;

    private ValidationRequest validationRequest;


    private UserUseCase userUseCase;

    @Test
    void testSaveUser() {
        CreateUserDTO createUserDTO = new CreateUserDTO(
                "juan",
                "perez",
                "juan.perez@gmail.com",
                "1234567890",
                "1234567",
                BigDecimal.valueOf(2000000),
                "carra 34#12-45",
                LocalDate.of(2000,03,03),
                1l
        );
        User user = new User(
                1l,
                "juan",
                "perez",
                "juan.perez@gmail.com",
                "1234567890",
                "1234567",
                BigDecimal.valueOf(2000000),
                "carra 34#12-45",
                LocalDate.of(2000,03,03),
                1l
        );
        UserDTO responseDTO = new UserDTO(
                "juan",
                "perez",
                "juan.perez@gmail.com",
                "1234567890",
                "1234567",
                BigDecimal.valueOf(2000000),
                "carra 34#12-45",
                LocalDate.of(2000,03,03),
                1l
        );

        Mockito.when(validationRequest.validate(Mockito.any(CreateUserDTO.class)))
                .thenReturn(Mono.just(createUserDTO));

        Mockito.when(userMapper.userDTOtoUser(Mockito.any(CreateUserDTO.class)))
                .thenReturn(user);

        Mockito.when(userUseCase.saveUser(Mockito.any(User.class)))
                .thenReturn(Mono.just(user));

        Mockito.when(userMapper.userToUserDTO(Mockito.any(User.class)))
                .thenReturn(responseDTO);

        webTestClient.post()
                .uri("/ruta-del-endpoint") // usa la ruta correcta que maneje el handler
                .bodyValue(createUserDTO)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(UserDTO.class)
                .isEqualTo(responseDTO);

        Mockito.verify(validationRequest).validate(Mockito.any(CreateUserDTO.class));
        Mockito.verify(userUseCase).saveUser(Mockito.any(User.class));
    }
}
