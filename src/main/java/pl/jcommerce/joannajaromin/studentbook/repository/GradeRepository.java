package pl.jcommerce.joannajaromin.studentbook.repository;

import org.springframework.data.jpa.repository.Query;
import pl.jcommerce.joannajaromin.studentbook.entity.Grade;

public interface GradeRepository extends MyJpaRepository<Grade,Integer> {

    @Query("SELECT g FROM Grade g JOIN FETCH g.student WHERE g.id = ?1")
    Grade findByIdWithStudentAndSubject(int gradeId);
}
