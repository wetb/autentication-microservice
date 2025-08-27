package co.com.bancolombia.api.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record CreateUserDTO (
        @NotBlank(message = "The name field is required.") String name,
        @NotBlank(message = "The last name field is required.") String lastName,
        @NotBlank(message = "The email field is required.") String email,
        String phone,
        String identityDocument,
        @NotNull(message = "The salary field is required.") BigDecimal salary,
        String address,
        LocalDate dateBirth,
        @NotNull(message = "The rol id field is required.") Long idRole
){
}
