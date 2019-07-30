package pl.jcommerce.joannajaromin.studentbook.service;

import org.junit.Before;
import org.junit.Test;
import pl.jcommerce.joannajaromin.studentbook.dto.EmailData;
import pl.jcommerce.joannajaromin.studentbook.dto.GradeDto;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaGradeConverter;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaSaveGradeConverter;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveGradeDto;
import pl.jcommerce.joannajaromin.studentbook.entity.Grade;
import pl.jcommerce.joannajaromin.studentbook.entity.Student;
import pl.jcommerce.joannajaromin.studentbook.entity.Subject;
import pl.jcommerce.joannajaromin.studentbook.repository.GradeRepository;
import pl.jcommerce.joannajaromin.studentbook.repository.StudentRepository;
import pl.jcommerce.joannajaromin.studentbook.repository.SubjectRepository;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GradeServiceTest {

    private static final int GRADE_ID = 1;
    private static final int STUDENT_ID = 1;
    private static final int SUBJECT_ID = 2;
    private static final int GRADE = 3;
    private static final String SUBJECT_NAME = "Historia";
    private GradeDto gradeDto;
    private GradeRepository gradeRepository;
    private Grade grade;
    private SaveGradeDto saveGradeDto;
    private GradeService gradeService;
    private OrikaGradeConverter gradeConverter;
    private OrikaSaveGradeConverter saveGradeConverter;
    private StudentRepository studentRepository;
    private SubjectRepository subjectRepository;
    private MailService mailService;
    private Student student;
    private Subject subject;

    @Before
    public void before(){
        gradeDto = new GradeDto(GRADE_ID,STUDENT_ID,SUBJECT_ID,GRADE);
        grade = new Grade();
        saveGradeDto = new SaveGradeDto();
        student = new Student();
        subject = new Subject();
        subject.setName(SUBJECT_NAME);
        gradeRepository = mock(GradeRepository.class);
        studentRepository = mock(StudentRepository.class);
        subjectRepository = mock(SubjectRepository.class);
        mailService = mock(MailService.class);
        gradeConverter = mock(OrikaGradeConverter.class);
        saveGradeConverter = mock(OrikaSaveGradeConverter.class);
        gradeService = new GradeServiceImpl(gradeRepository,gradeConverter,saveGradeConverter,mailService,subjectRepository,studentRepository);
        when(gradeConverter.map(grade,GradeDto.class)).thenReturn(gradeDto);
    }

    @Test
    public void canGetGradesList(){
        var gradeList = Arrays.asList(grade,grade);
        var gradeDtoList = Arrays.asList(gradeDto,gradeDto);
        when(gradeRepository.findAll()).thenReturn(gradeList);
        when(gradeConverter.mapAsList(gradeList,GradeDto.class)).thenReturn(gradeDtoList);
        var obtainedDtoList = gradeService.findAll();
        assertEquals(gradeDtoList,obtainedDtoList);
    }

    @Test
    public void canGetSingleGrade(){
        when(gradeRepository.findByIdCustom(GRADE_ID)).thenReturn(grade);
        var obtainedDto = gradeService.findById(GRADE_ID);
        assertEquals(gradeDto,obtainedDto);
    }

    @Test
    public void canSaveGrade() {
        when(saveGradeConverter.map(saveGradeDto,Grade.class)).thenReturn(grade);
        when(gradeRepository.save(grade)).thenReturn(grade);
        when(gradeConverter.map(grade,GradeDto.class)).thenReturn(gradeDto);
        when(subjectRepository.findByIdCustom(SUBJECT_ID)).thenReturn(subject);
        when(studentRepository.findByIdCustom(STUDENT_ID)).thenReturn(student);
        GradeDto savedGradeDto = gradeService.save(saveGradeDto);
        EmailData emailData = new EmailData(SUBJECT_NAME,GRADE,student);
        assertEquals(gradeDto,savedGradeDto);
        verify(mailService).sendEmailToStudentAboutNewGrade(emailData);
    }

    @Test
    public void willNotSendEmailIfSavedGradeIsNull (){
        when(saveGradeConverter.map(saveGradeDto,Grade.class)).thenReturn(grade);
        when(gradeRepository.save(grade)).thenReturn(null);
        when(gradeConverter.map(grade,GradeDto.class)).thenReturn(null);
        verify(mailService, never()).sendEmailToStudentAboutNewGrade(any());
    }

    @Test
    public void canDeleteGrade(){
        gradeService.deleteById(GRADE_ID);
        verify(gradeRepository).deleteById(GRADE_ID);
    }
}
