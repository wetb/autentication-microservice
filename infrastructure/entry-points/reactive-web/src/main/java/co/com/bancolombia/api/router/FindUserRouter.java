package co.com.bancolombia.api.router;

import co.com.bancolombia.api.Handler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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

@Configuration
@RequiredArgsConstructor
public class FindUserRouter {
    public static final String PATH= "/api/v1/users/{identityDocument}/exists";
    private final Handler handler;

    @Bean
    @RouterOperation(
            path = PATH,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = org.springframework.web.bind.annotation.RequestMethod.GET,
            beanClass = Handler.class,
            beanMethod = "existsByIdentityDocument",
            operation = @Operation(
                    operationId = "existsByIdentityDocument",
                    tags = {"users"},
                    summary = "Verify whether a user exists based on their ID document",
                    parameters = {
                            @Parameter(in = ParameterIn.PATH, name = "documentId", description = "ID of the document to search for", required = true, schema = @Schema(type = "integer", format = "int64"))
                    },
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Successful query. The response body indicates whether the user exists.",
                                    content = @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            examples = {
                                                    @ExampleObject(name = "User exists", value = "{\"exists\": true}"),
                                                    @ExampleObject(name = "User not exists", value = "{\"exists\": false}")
                                            }
                                    )
                            )
                    }
            )
    )
    public RouterFunction<ServerResponse> findUserByIdentityDocumentFunction() {
        return RouterFunctions.route(RequestPredicates.GET(PATH)
                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::existsUserByIdentityDocument);
    }
}
