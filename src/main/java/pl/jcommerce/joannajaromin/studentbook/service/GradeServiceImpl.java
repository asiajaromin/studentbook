package pl.jcommerce.joannajaromin.studentbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jcommerce.joannajaromin.studentbook.dto.GradeDto;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaGradeConverter;
import pl.jcommerce.joannajaromin.studentbook.entity.Grade;
import pl.jcommerce.joannajaromin.studentbook.repository.GradeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService{

    private final GradeRepository gradeRepository;
    private final OrikaGradeConverter converter;

    @Override
    public List<GradeDto> findAll() {
        List<Grade> grades = gradeRepository.findAll();
        List<GradeDto> gradeDto = converter.mapAsList(grades,GradeDto.class);
        return gradeDto;
    }

    @Override
    public GradeDto findById(int gradeId) {
        Grade grade = gradeRepository.findById(gradeId);
        GradeDto gradeDto = converter.map(grade, GradeDto.class);
        return gradeDto;
    }

    @Override
    public void save(GradeDto gradeDto) {
        Grade grade = converter.map(gradeDto,Grade.class);
        gradeRepository.save(grade);
    }

    @Override
    public void deleteById(int gradeId) {
        gradeRepository.deleteById(gradeId);
    }
}
