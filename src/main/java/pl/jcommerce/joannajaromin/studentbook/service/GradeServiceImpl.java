package pl.jcommerce.joannajaromin.studentbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jcommerce.joannajaromin.studentbook.dto.GradeDto;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaGradeConverter;
import pl.jcommerce.joannajaromin.studentbook.entity.Grade;
import pl.jcommerce.joannajaromin.studentbook.repository.GradeRepository;

import java.util.List;

@Service
public class GradeServiceImpl implements GradeService{

    private GradeRepository gradeRepository;

    private OrikaGradeConverter converter = new OrikaGradeConverter();

    @Autowired
    public GradeServiceImpl(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @Override
    public List<GradeDto> findAll() {
        List<Grade> grades = gradeRepository.findAll();
        List<GradeDto> gradeDto = converter.mapGradeListToGradeDtoList(grades);
        return gradeDto;
    }

    @Override
    public GradeDto findById(int gradeId) {
        Grade grade = gradeRepository.findById(gradeId);
        GradeDto gradeDto = converter.mapGradeToGradeDto(grade);
        return gradeDto;
    }

    @Override
    public void save(GradeDto gradeDto) {
        Grade grade = converter.mapGradeDtoToGrade(gradeDto);
        gradeRepository.save(grade);
    }

    @Override
    public void deleteById(int gradeId) {
        gradeRepository.deleteById(gradeId);
    }
}
