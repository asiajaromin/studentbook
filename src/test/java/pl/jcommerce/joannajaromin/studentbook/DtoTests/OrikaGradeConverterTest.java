package pl.jcommerce.joannajaromin.studentbook.DtoTests;

import org.junit.Before;
import org.junit.Test;
import pl.jcommerce.joannajaromin.studentbook.dto.GradeDto;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaGradeConverter;
import pl.jcommerce.joannajaromin.studentbook.entity.Grade;
import pl.jcommerce.joannajaromin.studentbook.entity.Student;
import pl.jcommerce.joannajaromin.studentbook.entity.Subject;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrikaGradeConverterTest {


    private final int GRADE_ID = 1;
    private final int GRADE = 2;
    private final int STUDENT_ID = 7;
    private final int SUBJECT_ID = 8;

    private OrikaGradeConverter gradeConverter;
    private Grade grade;
    private GradeDto gradeDto;
    private Student student;
    private Subject subject;

    @Before
    public void before(){
        student = new Student();
        student.setId(STUDENT_ID);
        subject = new Subject();
        subject.setId(SUBJECT_ID);
        grade = new Grade(GRADE_ID,student,subject,GRADE);
        gradeDto = new GradeDto(GRADE_ID,STUDENT_ID,SUBJECT_ID,GRADE);
        gradeConverter = new OrikaGradeConverter();
    }

    @Test
    public void canConvertSingleDtoToEntity(){
        Grade mappedGrade = gradeConverter.map(gradeDto,Grade.class);
        assertEquals(gradeDto.getId(),mappedGrade.getId());
        assertEquals(gradeDto.getStudentId(),mappedGrade.getStudent().getId());
        assertEquals(gradeDto.getSubjectId(),mappedGrade.getSubject().getId());
        assertEquals(gradeDto.getGrade(),mappedGrade.getGrade());
    }

    @Test
    public void canConvertSingleEntityToDto(){
        GradeDto mappedGradeDto = gradeConverter.map(grade,GradeDto.class);
        assertEquals(grade.getId(),mappedGradeDto.getId());
        assertEquals(grade.getStudent().getId(),gradeDto.getStudentId());
        assertEquals(grade.getSubject().getId(),gradeDto.getSubjectId());
        assertEquals(grade.getGrade(),mappedGradeDto.getGrade());
    }

    @Test
    public void canConvertDtoListToEntityList() {
        List<GradeDto> dtoList = new ArrayList<>();
        dtoList.add(gradeDto);
        dtoList.add(gradeDto);
        List<Grade> entityList = gradeConverter.mapAsList(dtoList,Grade.class);
        Grade secondEntity = entityList.get(1);
        GradeDto secondDto = dtoList.get(1);
        assertEquals(secondDto.getId(),secondEntity.getId());
        assertEquals(secondDto.getStudentId(),secondEntity.getStudent().getId());
        assertEquals(secondDto.getSubjectId(),secondEntity.getSubject().getId());
        assertEquals(secondDto.getGrade(),secondEntity.getGrade());
    }

    @Test
    public void canConvertEntityListToDtoList(){
        List<Grade> entityList = new ArrayList<>();
        entityList.add(grade);
        entityList.add(grade);
        entityList.add(grade);
        List<GradeDto> dtoList = gradeConverter.mapAsList(entityList,GradeDto.class);
        Grade thirdEntity = entityList.get(2);
        GradeDto thirdDto = dtoList.get(2);
        assertEquals(thirdEntity.getId(),thirdDto.getId());
        assertEquals(thirdEntity.getSubject().getId(),thirdDto.getSubjectId());
        assertEquals(thirdEntity.getStudent().getId(),thirdDto.getStudentId());
        assertEquals(thirdEntity.getGrade(),thirdDto.getGrade());
    }
}
