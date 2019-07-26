package pl.jcommerce.joannajaromin.studentbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jcommerce.joannajaromin.studentbook.dto.GradeDto;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaGradeConverter;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaSaveGradeConverter;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveGradeDto;
import pl.jcommerce.joannajaromin.studentbook.entity.Grade;
import pl.jcommerce.joannajaromin.studentbook.entity.Student;
import pl.jcommerce.joannajaromin.studentbook.exception.GradeNotFoundException;
import pl.jcommerce.joannajaromin.studentbook.repository.GradeRepository;
import pl.jcommerce.joannajaromin.studentbook.repository.StudentRepository;
import pl.jcommerce.joannajaromin.studentbook.repository.SubjectRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService{

    private final GradeRepository gradeRepository;
    private final OrikaGradeConverter converter;
    private final OrikaSaveGradeConverter saveConverter;
    private final MailService mailService;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    @Override
    @Transactional(readOnly = true)
    public List<GradeDto> findAll() {
        var grades = gradeRepository.findAll();
        return Optional.ofNullable(grades)
                .map(grade->converter.mapAsList(grade,GradeDto.class))
                .orElseThrow(()->new GradeNotFoundException("Brak ocen do wyświetlenia"));
    }

    @Override
    @Transactional(readOnly = true)
    public GradeDto findById(int gradeId) {
        var grade = gradeRepository.findById(gradeId);
        return Optional.ofNullable(grade)
                .map(grade1 -> converter.map(grade1,GradeDto.class))
                .orElseThrow(()->new GradeNotFoundException("Brak oceny o id = " + gradeId));
    }

    @Override
    @Transactional
    public GradeDto save(SaveGradeDto saveGradeDto) {
        var grade = saveConverter.map(saveGradeDto,Grade.class);
        var saved = gradeRepository.save(grade);
        GradeDto gradeDto = converter.map(saved, GradeDto.class);
        int subjectId = gradeDto.getSubjectId();
        int studentId = gradeDto.getStudentId();
        String subjectName = subjectRepository.findNameById(subjectId);
        Student student = studentRepository.findById(studentId);
        int gradeInt = gradeDto.getGrade();
        try {
            mailService.sendEmailToStudent(gradeInt,subjectName,student);
        }
        catch (MailException mailException){
            throw mailException;
        }

        return gradeDto;
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
