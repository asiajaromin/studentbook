package pl.jcommerce.joannajaromin.studentbook.repository;

import pl.jcommerce.joannajaromin.studentbook.entity.User;

public interface UserRepository extends MyJpaRepository<User,Integer> {
    User findByUsername(String username);
}
