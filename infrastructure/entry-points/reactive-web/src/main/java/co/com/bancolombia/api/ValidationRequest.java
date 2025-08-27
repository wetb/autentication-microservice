package co.com.bancolombia.api;

import co.com.bancolombia.api.exceptions.ExceptionsValidation;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ValidationRequest {
    private final Validator validator;

    public ValidationRequest(Validator validator){
        this.validator = validator;
    }

    public <T> Mono<T> validate(T dto) {
        return Mono.fromCallable(() -> {
            var errors = new BeanPropertyBindingResult(dto, dto.getClass().getName());
            validator.validate(dto, errors);
            if (errors.hasErrors()) {
                Set<String> errorMessage = errors.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.toSet());
                throw new ExceptionsValidation("Validation failed", errorMessage);
            }
            return dto;
        });
    }

}
