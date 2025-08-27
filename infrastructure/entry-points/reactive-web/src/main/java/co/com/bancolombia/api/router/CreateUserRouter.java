package co.com.bancolombia.api.router;

import co.com.bancolombia.api.Handler;
import co.com.bancolombia.api.dto.request.CreateUserDTO;
import co.com.bancolombia.api.dto.response.UserDTO;
import co.com.bancolombia.api.exceptions.ExceptionsValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
@RequiredArgsConstructor
public class CreateUserRouter {
    public  static final String PATH= "/api/v1/usuarios";
    private final Handler handler;

    @Bean
    @RouterOperation(
            path = PATH,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = org.springframework.web.bind.annotation.RequestMethod.POST,
            beanClass = Handler.class,
            beanMethod = "saveUser",
            operation = @Operation(
                    operationId = "saveUser",
                    tags = {"users"},
                    summary = "save a new user",
                    requestBody = @RequestBody(
                            description = "user information to be saved",
                            required = true,
                            content = @Content(
                                    schema = @Schema(implementation = CreateUserDTO.class)
                            )
                    ),
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "user successfully saved",
                                    content = @Content(schema = @Schema(implementation = UserDTO.class))
                            ),
                            @ApiResponse(
                                    responseCode = "400",
                                    description = "invalid request",
                                    content = @Content(
                                            schema = @Schema(implementation = ExceptionsValidation.class),
                                            examples = @ExampleObject(
                                                    name = "Example of error response 400",
                                                    value = "{\"status\": 400,\"message\": \"email unavailable\",\"errors\": [\"email\"]}"
                                            )
                                    )
                            )
                    }
            )
    )
    public RouterFunction<ServerResponse> usuarioRouterFunction() {
        return RouterFunctions.route(POST(PATH).and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::saveUser);
    }
}
