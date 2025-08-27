package co.com.pragma.api;

import co.com.pragma.api.DTO.UserRequestDTO;
import co.com.pragma.api.DTO.UserResponseDTO;
import co.com.pragma.model.user.User;
import co.com.pragma.usecase.registeruser.RegisterUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class Handler {
    private final RegisterUserUseCase registerUserUseCase;

    @Operation(summary = "Registrar un nuevo usuario", description = "Crea un usuario en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "Usuario ya existe")
    })

    public Mono<ServerResponse> register(ServerRequest request) {
        return request.bodyToMono(UserRequestDTO.class)
                .map(this::toDomain)
                .flatMap(registerUserUseCase::registerUser)
                .flatMap(user -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(toResponse(user)))
                .onErrorResume(e -> ServerResponse
                        .badRequest()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(Map.of("error", e.getMessage())));
    }

    private User toDomain(UserRequestDTO entity) {
        return User.builder()
                .name(entity.getName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .birthday(entity.getBirthday())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .baseSalary(entity.getBaseSalary())
                .build();
    }

    private UserResponseDTO toResponse(User user) {
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
    }
}
