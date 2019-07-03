package pl.jcommerce.joannajaromin.studentbook.DtoTests;

import ma.glasnost.orika.MapperFacade;
import org.junit.Before;
import org.junit.Test;
import pl.jcommerce.joannajaromin.studentbook.dto.GradeDto;
import pl.jcommerce.joannajaromin.studentbook.entity.Grade;
import pl.jcommerce.joannajaromin.studentbook.entity.Student;
import pl.jcommerce.joannajaromin.studentbook.entity.Subject;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class OrikaGradeConverterTest {


    private final int GRADE_ID = 1;
    private final int GRADE = 2;
    private final int STUDENT_ID = 7;
    private final int SUBJECT_ID = 8;

    private MapperFacade mapperFacade;
    private Grade grade;
    private GradeDto gradeDto;
    private Student student;
    private Subject subject;

    @Before
    public void before(){
        grade = mock(Grade.class);
        student = mock(Student.class);
        subject = mock(Subject.class);
        gradeDto = mock(GradeDto.class);
        when(grade.getId()).thenReturn(GRADE_ID);
        when(grade.getStudent()).thenReturn(student);
        when(grade.getSubject()).thenReturn(subject);
        when(grade.getGrade()).thenReturn(GRADE);
        when(gradeDto.getId()).thenReturn(GRADE_ID);
        when(gradeDto.getStudentId()).thenReturn(STUDENT_ID);
        when(gradeDto.getSubjectId()).thenReturn(SUBJECT_ID);
        when(gradeDto.getGrade()).thenReturn(GRADE);
        when(student.getId()).thenReturn(STUDENT_ID);
        when(subject.getId()).thenReturn(SUBJECT_ID);
    }

    @Test
    public void canConvertSingleDtoToEntity(){
        Grade mappedGrade = mapperFacade.map(gradeDto,Grade.class);
        assertEquals(gradeDto.getId(),mappedGrade.getId());
//        assertEquals(gradeDto.getStudentId(),mappedGrade.getStudent().getId());
//        assertEquals(gradeDto.getSubjectId(),mappedGrade.getSubject().getId());
        assertEquals(gradeDto.getGrade(),mappedGrade.getGrade());
    }

    @Test
    public void canConvertSingleEntityToDto(){
        GradeDto mappedGradeDto = mapperFacade.map(grade,GradeDto.class);

    }

    @Test
    public void canConvertDtoListToEntityList(){

    }

    @Test
    public void canConvertEntityListToDtoList(){

    }
}
