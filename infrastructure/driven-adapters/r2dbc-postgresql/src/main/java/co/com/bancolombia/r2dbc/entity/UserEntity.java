package co.com.bancolombia.r2dbc.entity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(value = "users")
public class UserEntity {

    @Id
    @Column("id_user")
    private Long idUser;
    private String name;
    @Column("last_name")
    private String lastName;
    private String email;
    private String phone;
    @Column("identity_document")
    private String identityDocument;
    private BigDecimal salary;
    private String address;
    @Column("date_birth")
    private LocalDate dateBirth;
    @Column("id_rol")
    private Long idRole;
}

