package pl.jcommerce.joannajaromin.studentbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(readOnly = true)
    public List<GradeDto> findAll() {
        var grades = gradeRepository.findAll();
        var gradeDto = converter.mapAsList(grades,GradeDto.class);
        return gradeDto;
    }

    @Override
    @Transactional(readOnly = true)
    public GradeDto findById(int gradeId) {
        var grade = gradeRepository.findById(gradeId);
        var gradeDto = converter.map(grade, GradeDto.class);
        return gradeDto;
    }

    @Override
    @Transactional
    public GradeDto save(SaveGradeDto saveGradeDto) {
        var grade = saveConverter.map(saveGradeDto,Grade.class);
        var saved = gradeRepository.save(grade);
        return converter.map(saved, GradeDto.class);
    }

    @Override
    @Transactional
    public GradeDto update(GradeDto gradeDto) {
        var grade = converter.map(gradeDto,Grade.class);
        var saved = gradeRepository.save(grade);
        return converter.map(saved, GradeDto.class);
    }

    @Override
    @Transactional
    public void deleteById(int gradeId) {
        gradeRepository.deleteById(gradeId);
    }
}
