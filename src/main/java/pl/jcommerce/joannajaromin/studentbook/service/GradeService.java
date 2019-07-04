package pl.jcommerce.joannajaromin.studentbook.service;

import pl.jcommerce.joannajaromin.studentbook.dto.GradeDto;

import java.util.List;

public interface GradeService {
    List<GradeDto> findAll();

    GradeDto findById(int gradeId);

    GradeDto save(GradeDto grade);

    void deleteById(int gradeId);
}
