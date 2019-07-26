package pl.jcommerce.joannajaromin.studentbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.jcommerce.joannajaromin.studentbook.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject,Integer> {

    Subject findById(int subjectId);

    @Query("select s.name from Subject s where s.id = ?1")
    String findNameById(int subjectId);
}
