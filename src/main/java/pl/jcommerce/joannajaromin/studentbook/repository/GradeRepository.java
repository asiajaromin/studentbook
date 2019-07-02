package pl.jcommerce.joannajaromin.studentbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.jcommerce.joannajaromin.studentbook.entity.Grade;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade,Integer> {

    Grade findById(int gradeId);

}
