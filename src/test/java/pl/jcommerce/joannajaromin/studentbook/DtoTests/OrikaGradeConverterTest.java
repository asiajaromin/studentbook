package pl.jcommerce.joannajaromin.studentbook.DtoTests;

import org.junit.Before;
import org.junit.Test;
import pl.jcommerce.joannajaromin.studentbook.dto.GradeDto;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaGradeConverter;
import pl.jcommerce.joannajaromin.studentbook.entity.Grade;
import pl.jcommerce.joannajaromin.studentbook.entity.Student;
import pl.jcommerce.joannajaromin.studentbook.entity.Subject;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class OrikaGradeConverterTest {

    private final int GRADE_ID1 = 1;
    private final int GRADE_ID2 = 45;
    private final int GRADE_ID3 = 32;
    private final int GRADE1 = 2;
    private final int GRADE2 = 5;
    private final int STUDENT_ID = 7;
    private final int SUBJECT_ID = 8;

    private OrikaGradeConverter gradeConverter;
    private Student student;
    private Subject subject;

    @Before
    public void before(){
        student = new Student();
        student.setId(STUDENT_ID);
        subject = new Subject();
        subject.setId(SUBJECT_ID);
        gradeConverter = new OrikaGradeConverter();
    }

    @Test
    public void canConvertSingleDtoToGrade(){
        var gradeDto = new GradeDto(GRADE_ID1,STUDENT_ID,SUBJECT_ID,GRADE1);
        var mappedGrade = gradeConverter.map(gradeDto, Grade.class);
        assertEquals(gradeDto.getId(), mappedGrade.getId());
        assertEquals(gradeDto.getStudentId(), mappedGrade.getStudent().getId());
        assertEquals(gradeDto.getSubjectId(), mappedGrade.getSubject().getId());
        assertEquals(gradeDto.getGrade(), mappedGrade.getGrade());
    }

    @Test
    public void canConvertSingleGradeToDto(){
        var grade = new Grade(GRADE_ID2,student,subject,GRADE2);
        var mappedGradeDto = gradeConverter.map(grade, GradeDto.class);
        assertEquals(grade.getId(), mappedGradeDto.getId());
        assertEquals(grade.getStudent().getId(), mappedGradeDto.getStudentId());
        assertEquals(grade.getSubject().getId(), mappedGradeDto.getSubjectId());
        assertEquals(grade.getGrade(), mappedGradeDto.getGrade());
    }

    @Test
    public void canConvertDtoListToGradesList() {
        var gradeDto1 = new GradeDto(GRADE_ID2,STUDENT_ID,SUBJECT_ID,GRADE2);
        var gradeDto2 = new GradeDto(GRADE_ID1,STUDENT_ID,SUBJECT_ID,GRADE1);
        var gradeDto3 = new GradeDto(GRADE_ID3,STUDENT_ID,SUBJECT_ID,GRADE2);
        var dtoList = Arrays.asList(gradeDto1,gradeDto2,gradeDto3);
        var gradeList = gradeConverter.mapAsList(dtoList, Grade.class);
        assertEquals(dtoList.size(),gradeList.size());
        for (var i = 0; i < dtoList.size(); i++) {
            var grade = gradeList.get(i);
            var gradeDto = dtoList.get(i);
            assertEquals(gradeDto.getId(), grade.getId());
            assertEquals(gradeDto.getStudentId(), grade.getStudent().getId());
            assertEquals(gradeDto.getSubjectId(), grade.getSubject().getId());
            assertEquals(gradeDto.getGrade(), grade.getGrade());
        }
    }

    @Test
    public void canConvertGradesListToDtoList(){
        var grade1 = new Grade(GRADE_ID1,student,subject,GRADE2);
        var grade2 = new Grade(GRADE_ID2,student,subject,GRADE1);
        var gradeList = Arrays.asList(grade2,grade1);
        var dtoList = gradeConverter.mapAsList(gradeList, GradeDto.class);
        assertEquals(gradeList.size(),dtoList.size());
        for (var i = 0; i<gradeList.size(); i++) {
            var grade = gradeList.get(i);
            var gradeDto = dtoList.get(i);
            assertEquals(grade.getId(), gradeDto.getId());
            assertEquals(grade.getSubject().getId(), gradeDto.getSubjectId());
            assertEquals(grade.getStudent().getId(), gradeDto.getStudentId());
            assertEquals(grade.getGrade(), gradeDto.getGrade());
        }
    }
}
