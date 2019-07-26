package pl.jcommerce.joannajaromin.studentbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.jcommerce.joannajaromin.studentbook.entity.Student;

public interface StudentRepository extends JpaRepository<Student,Integer> {

    Student findById(int studentId);
}
