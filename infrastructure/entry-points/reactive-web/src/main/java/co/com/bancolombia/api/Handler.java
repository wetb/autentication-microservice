package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.request.CreateUserDTO;
import co.com.bancolombia.api.mapper.UserMapper;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class Handler {
    private final UserMapper userMapper;
    private final ValidationRequest validationRequest;
    private final UserUseCase userUseCase;

    public Mono<ServerResponse> saveUser(ServerRequest serverRequest){
        return serverRequest.bodyToMono(CreateUserDTO.class)
                .doOnNext(dto -> log.info(" Starting user save flow: {}", dto))
                .flatMap(validationRequest::validate)
                .map(userMapper::userDTOtoUser)
                .flatMap(userUseCase::saveUser)
                .map(userMapper::userToUserDTO)
                .doOnSuccess(responseDTO -> {
                    log.info("User successfully saved: {}", responseDTO);
                })
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response)
                )
                .doOnError(e -> log.error("Error saving user: {}", e.getMessage()));
    }

    public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
        // useCase.logic();
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> listenGETOtherUseCase(ServerRequest serverRequest) {
        // useCase2.logic();
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {
        // useCase.logic();
        return ServerResponse.ok().bodyValue("");
    }
}
