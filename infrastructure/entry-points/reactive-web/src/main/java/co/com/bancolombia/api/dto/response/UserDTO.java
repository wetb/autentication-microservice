package co.com.bancolombia.api.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UserDTO (
        String name,
        String lastName,
         String email,
        String phone,
        String identityDocument,
        BigDecimal salary,
        String address,
        LocalDate dateBirth,
        Long idRole
) {
}
