package co.com.pragma.jpa.repository;

import co.com.pragma.jpa.JPARepository;
import co.com.pragma.jpa.entity.UserEntity;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDataRepository implements UserRepository {

    private final JPARepository jpaRepository;

    @Override
    public User save(User user) {
        UserEntity entity = toEntity(user);
        UserEntity saved = jpaRepository.save(entity);
        return toModel(saved);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(this::toModel);
    }

    private UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .birthday(user.getBirthday())
                .address(user.getAddress())
                .phone(user.getPhone())
                .baseSalary(user.getBaseSalary())
                .build();
    }

    private User toModel(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .name(entity.getName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .birthday(entity.getBirthday())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .baseSalary(entity.getBaseSalary())
                .build();
    }
}
