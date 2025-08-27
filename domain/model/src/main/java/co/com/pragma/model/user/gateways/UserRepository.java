package co.com.pragma.model.user.gateways;

import co.com.pragma.model.user.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findByEmail(String email);
}
