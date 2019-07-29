package pl.jcommerce.joannajaromin.studentbook.service;

import pl.jcommerce.joannajaromin.studentbook.dto.GradeDto;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveGradeDto;

import javax.mail.MessagingException;
import java.util.List;

public interface GradeService {
    List<GradeDto> findAll();

    GradeDto findById(int gradeId);

    GradeDto save(SaveGradeDto grade) throws MessagingException;

    void deleteById(int gradeId);

    GradeDto update(GradeDto grade);
}
