package pl.jcommerce.joannajaromin.studentbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.jcommerce.joannajaromin.studentbook.entity.Homework;

public interface HomeworkRepository extends JpaRepository<Homework,Integer> {
}
