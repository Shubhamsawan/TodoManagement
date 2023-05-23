package net.myproject.todo.Repository;

import net.myproject.todo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    //User object by the se email is exists or not
    Boolean existsByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);


}
