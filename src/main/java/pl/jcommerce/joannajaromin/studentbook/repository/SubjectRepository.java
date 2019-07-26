package pl.jcommerce.joannajaromin.studentbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.jcommerce.joannajaromin.studentbook.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject,Integer> {

    Subject findById(int subjectId);
}
