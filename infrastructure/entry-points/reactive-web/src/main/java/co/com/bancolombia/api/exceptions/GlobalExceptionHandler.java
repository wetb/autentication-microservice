package co.com.bancolombia.api;

import co.com.bancolombia.api.exceptions.ExceptionsValidation;
import co.com.bancolombia.model.exceptions.DomainException;
import co.com.bancolombia.model.exceptions.gateways.ApplicationError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

@Component
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {
    private final Map<Class<? extends Throwable>, BiFunction<ServerWebExchange, Throwable, Mono<Void>>> handlers;
    private final ObjectMapper objectMapper;

    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.handlers = Map.of(
                ExceptionsValidation.class, this::handleApplicationError,
                DomainException.class, this::handleApplicationError
        );
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        return handlers.getOrDefault(ex.getClass(), this::handleGenericError)
                .apply(exchange, ex);
    }

    private Mono<Void> handleGenericError(ServerWebExchange exchange, Throwable throwable) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        return writeResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "An unexpected error has occurred.");
    }

    private Mono<Void> handleApplicationError(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.BAD_REQUEST);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Set<String> errors = Set.of("An unexpected system error has occurred.");

        if (ex instanceof ApplicationError)
            errors = ((ApplicationError) ex).getFields();

        return writeResponse(response, HttpStatus.BAD_REQUEST, ex.getMessage(), errors);
    }


    private Mono<Void> writeResponse(ServerHttpResponse response, HttpStatus status, String message, String details) {
        return Mono.defer(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            String responseBody = String.format("{\"status\": %d, \"message\": \"%s\", \"errors\": \"%s\"}",
                    status.value(), message, details);
            return response.writeWith(Mono.just(bufferFactory.wrap(responseBody.getBytes())));
        });
    }

    private Mono<Void> writeResponse(ServerHttpResponse response, HttpStatus status, String message, Set<String> errors) {
        return Mono.defer(() -> {
            try {
                DataBufferFactory bufferFactory = response.bufferFactory();
                Map<String, Object> errorMap = Map.of(
                        "status", status.value(),
                        "message", message,
                        "errors", errors
                );
                byte[] bytes = objectMapper.writeValueAsBytes(errorMap);
                return response.writeWith(Mono.just(bufferFactory.wrap(bytes)));
            } catch (JsonProcessingException e) {
                return Mono.error(new IllegalStateException("Error processing JSON response", e));
            }
        });
    }
}
