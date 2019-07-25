package pl.jcommerce.joannajaromin.studentbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.jcommerce.joannajaromin.studentbook.entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);
}
