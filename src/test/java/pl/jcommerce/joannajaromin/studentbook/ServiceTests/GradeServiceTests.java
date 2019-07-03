package pl.jcommerce.joannajaromin.studentbook.ServiceTests;

import ma.glasnost.orika.MapperFacade;
import org.junit.Before;
import org.junit.Test;
import pl.jcommerce.joannajaromin.studentbook.dto.GradeDto;
import pl.jcommerce.joannajaromin.studentbook.entity.Grade;
import pl.jcommerce.joannajaromin.studentbook.entity.Student;
import pl.jcommerce.joannajaromin.studentbook.entity.Subject;
import pl.jcommerce.joannajaromin.studentbook.repository.GradeRepository;
import pl.jcommerce.joannajaromin.studentbook.service.GradeService;
import pl.jcommerce.joannajaromin.studentbook.service.GradeServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class GradeServiceTests {

    private final int GRADE_ID = 1;
    private final int GRADE = 2;
    private List<Grade> gradeList = getGradeList();
    private GradeDto gradeDto;
    private Student student;
    private Subject subject;
    private GradeRepository gradeRepository;
    private Grade grade;
    private GradeService gradeService;
    private MapperFacade mapperFacade;

    private List<Grade> getGradeList(){
        Grade grade = new Grade();
        List<Grade> gradeList = new ArrayList<>();
        gradeList.add(grade);
        gradeList.add(grade);
        return gradeList;
    }

    @Before
    public void before(){
        gradeDto = mock(GradeDto.class);
        student = mock(Student.class);
        subject = mock(Subject.class);
        gradeRepository = mock(GradeRepository.class);
        grade = mock(Grade.class);
        mapperFacade = mock(MapperFacade.class);
        gradeService = new GradeServiceImpl(gradeRepository,mapperFacade);
        when(grade.getId()).thenReturn(GRADE_ID);
        when(grade.getStudent()).thenReturn(student);
        when(grade.getSubject()).thenReturn(subject);
        when(grade.getGrade()).thenReturn(GRADE);
        when(gradeRepository.findById(GRADE_ID)).thenReturn(grade);
        when(gradeRepository.findAll()).thenReturn(gradeList);
    }

    @Test
    public void canGetGradesList(){
        gradeService.findAll();
        verify(gradeRepository).findAll();
    }

    @Test
    public void canGetSingleGrade(){
        gradeService.findById(GRADE_ID);
        verify(gradeRepository).findById(GRADE_ID);
    }

    @Test
    public void canSaveGrade(){
        gradeService.save(gradeDto);
        Grade gradeDtoTranslatedToGrade = mapperFacade.map(gradeDto, Grade.class);
        verify(gradeRepository).save(gradeDtoTranslatedToGrade);
    }

    @Test
    public void canDeleteGrade(){
        gradeService.deleteById(GRADE_ID);
        verify(gradeRepository).deleteById(GRADE_ID);
    }
}
