package co.com.bancolombia.api.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record CreateUserDTO (
        @NotBlank(message = "The name field is required.") String name,
        @NotBlank(message = "The last name field is required.") String lastName,
        @NotBlank(message = "The email field is required.")
        @Email(message = "invalid format email")
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
        String email,
        String phone,
        String identityDocument,
        @NotNull(message = "The salary field is required.")
        @PositiveOrZero(message = "salary must be greater than 0")
        @DecimalMax(value = "15000000", message = "salary cannot exceed 15,000,000")
        BigDecimal salary,
        String address,
        LocalDate dateBirth,
        @NotNull(message = "The rol id field is required.") Long idRole
){
}
