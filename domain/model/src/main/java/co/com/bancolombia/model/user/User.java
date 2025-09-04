package co.com.bancolombia.model.user;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class User {
    private Long idUser;
    private  String name;
    private String lastName;
    private String email;
    private String phone;
    private String identityDocument;
    private BigDecimal salary;
    private String address;
    private LocalDate dateBirth;
    private Long idRole;

}
