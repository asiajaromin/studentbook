package pl.jcommerce.joannajaromin.studentbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jcommerce.joannajaromin.studentbook.dto.GradeDto;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaGradeConverter;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaSaveGradeConverter;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveGradeDto;
import pl.jcommerce.joannajaromin.studentbook.entity.Grade;
import pl.jcommerce.joannajaromin.studentbook.repository.GradeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService{

    private final GradeRepository gradeRepository;
    private final OrikaGradeConverter converter;
    private final OrikaSaveGradeConverter saveConverter;

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
    public GradeDto save(SaveGradeDto saveGradeDto) {
        Grade grade = saveConverter.map(saveGradeDto,Grade.class);
        Grade saved = gradeRepository.save(grade);
        return converter.map(saved, GradeDto.class);
    }

    @Override
    public GradeDto update(GradeDto gradeDto) {
        Grade grade = converter.map(gradeDto,Grade.class);
        Grade saved = gradeRepository.save(grade);
        return converter.map(saved, GradeDto.class);
    }

    @Override
    public void deleteById(int gradeId) {
        gradeRepository.deleteById(gradeId);
    }
}
