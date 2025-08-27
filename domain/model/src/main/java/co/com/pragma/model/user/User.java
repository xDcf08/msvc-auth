package co.com.pragma.model.user;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    private Long id;
    private String name;
    private String lastName;
    private String email;
    private LocalDate birthday;
    private String address;
    private String phone;
    private BigDecimal baseSalary;

    private User(String name, String lastName, LocalDate birthday,
                 String address, String phone, String email,
                 BigDecimal baseSalary) {

        this.name = Objects.requireNonNull(name, "Los nombres son obligatorios");
        this.lastName = Objects.requireNonNull(lastName, "Los apellidos son obligatorios");
        this.birthday = Objects.requireNonNull(birthday, "La fecha de nacimiento es obligatoria");
        this.address = Objects.requireNonNull(address, "La dirección es obligatoria");
        this.phone = Objects.requireNonNull(phone, "El teléfono es obligatorio");
        this.email = Objects.requireNonNull(email, "El correo es obligatorio");
        this.baseSalary = Objects.requireNonNull(baseSalary, "El salario base es obligatorio");

        if (baseSalary.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El salario base debe ser mayor a 0");
        }
    }

    public static User of(String name, String lastName, LocalDate birthday,
                           String address, String phone, String email,
                           BigDecimal baseSalary){

        return new User(name, lastName, birthday, address, phone, email,baseSalary);
    }

}
