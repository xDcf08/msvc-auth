package co.com.pragma.api.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    @NotBlank(message = "El nombre no puede estar vacio")
    private String name;
    @NotBlank(message = "El apellido no puede estar vacio")
    private String lastName;
    @NotBlank(message = "El email no puede estar vacio")
    @Email(message = "El formato del correo no es el correcto")
    private String email;
    private LocalDate birthday;
    private String address;
    private String phone;
    @NotBlank(message = "El salario base no puede estar vacio")
    @Min(0)
    @Max(15000000)
    private BigDecimal baseSalary;
}
