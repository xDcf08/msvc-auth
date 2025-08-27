package co.com.pragma.usecase.registeruser;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RegisterUserUseCase {

    private final UserRepository userRepository;

    public Mono<User> registerUser(User user){

        try{
            validateUser(user);

            var existingUser = userRepository.findByEmail(user.getEmail());
            if(existingUser.isPresent()){
                throw new IllegalArgumentException("El correo electronico ya está registrado");
            }

            var savedUser = userRepository.save(user);
            return Mono.just(savedUser);

        }catch (IllegalArgumentException e){
            throw e;
        }
        catch (Exception e){
            throw new RuntimeException("");
        }
    }

    private void validateUser(User user){
        if(user == null){
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
        if(isNullOrEmpty(user.getName())){
            throw new IllegalArgumentException("El nombre es obligatorio");
        }

        if(isNullOrEmpty(user.getLastName())){
            throw new IllegalArgumentException("El apellido es obligatorio");
        }

        if(isNullOrEmpty(user.getEmail()) || validateEmail(user.getEmail())){
            throw new IllegalArgumentException("El email es obligatorio");
        }

        if(user.getBaseSalary() == null){
            throw new IllegalArgumentException("El salario base es obligatorio");
        }

        if(user.getBaseSalary().doubleValue() < 0 || user.getBaseSalary().doubleValue() > 15_000_000){
            throw new IllegalArgumentException("El salario base debe estar entre 0 y 15000000");
        }
    }

    private boolean isNullOrEmpty(String value){
        return value == null || value.trim().isEmpty();
    }

    private boolean validateEmail(String value){
        return false;
    }
}
