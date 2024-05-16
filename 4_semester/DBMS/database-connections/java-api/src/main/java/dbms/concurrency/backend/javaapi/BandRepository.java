package dbms.concurrency.backend.javaapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

import dbms.concurrency.backend.javaapi.User;
public interface UserRepository extends JpaRepository<User, Integer> {
//    @Query("select user from users where user.id = :id")

}
