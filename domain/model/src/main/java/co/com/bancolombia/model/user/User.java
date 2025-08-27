package co.com.bancolombia.model.user;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

//@NoArgsConstructor
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
    private String idRole;

    public User() {
    }

    public User(Long idUser, String name, String lastName, String email, String phone, String identityDocument, BigDecimal salary, String address, LocalDate dateBirth, String idRole) {
        this.idUser = idUser;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.identityDocument = identityDocument;
        this.salary = salary;
        this.address = address;
        this.dateBirth = dateBirth;
        this.idRole = idRole;
    }

    public Long getIdUser() {
        return idUser;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getIdentityDocument() {
        return identityDocument;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public String getIdRole() {
        return idRole;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setIdentityDocument(String identityDocument) {
        this.identityDocument = identityDocument;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDateBirth(LocalDate dateBirth) {
        this.dateBirth = dateBirth;
    }

    public void setIdRole(String idRole) {
        this.idRole = idRole;
    }
}
