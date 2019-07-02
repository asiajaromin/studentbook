package pl.jcommerce.joannajaromin.studentbook.ServiceTests;

import org.junit.Before;
import org.junit.Test;
import pl.jcommerce.joannajaromin.studentbook.dto.GradeDto;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaGradeConverter;
import pl.jcommerce.joannajaromin.studentbook.entity.Grade;
import pl.jcommerce.joannajaromin.studentbook.entity.Student;
import pl.jcommerce.joannajaromin.studentbook.entity.Subject;
import pl.jcommerce.joannajaromin.studentbook.repository.GradeRepository;
import pl.jcommerce.joannajaromin.studentbook.service.GradeServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class GradeServiceTests {

    private GradeRepository gradeRepository = mock(GradeRepository.class);
    private Grade grade = mock(Grade.class);
    private List<Grade> gradeList = getGradeList();
    private GradeDto gradeDto = mock(GradeDto.class);
    private Student student = mock(Student.class);
    private Subject subject = mock(Subject.class);
    private final int GRADE_ID = 1;
    private final int GRADE = 2;

    private GradeServiceImpl gradeService = new GradeServiceImpl(gradeRepository);

    private List<Grade> getGradeList(){
        List<Grade> gradeList = new ArrayList<>();
        gradeList.add(grade);
        gradeList.add(grade);
        return gradeList;
    }

    @Before
    public void before(){
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
    public void gradeIsTranslatedIntoGradeDto(){
        GradeDto gradeDto = gradeService.findById(GRADE_ID);
        assertEquals(grade.getId(),gradeDto.getId());
        assertEquals(grade.getStudent().getId(),gradeDto.getStudentId());
        assertEquals(grade.getSubject().getId(),gradeDto.getSubjectId());
        assertEquals(grade.getGrade(),gradeDto.getGrade());
    }

    @Test
    public void gradeListIsTranslatedIntoGradeDtoList(){
        List<GradeDto> gradeDtoList = gradeService.findAll();
        assertEquals(gradeList.get(1).getId(),gradeDtoList.get(1).getId());
        assertEquals(gradeList.get(1).getStudent().getId(),gradeDtoList.get(1).getStudentId());
        assertEquals(gradeList.get(1).getSubject().getId(),gradeDtoList.get(1).getSubjectId());
        assertEquals(gradeList.get(1).getGrade(),gradeDtoList.get(1).getGrade());
    }

    @Test
    public void canSaveGrade(){
        gradeService.save(gradeDto);
        OrikaGradeConverter converter = new OrikaGradeConverter();
        Grade gradeDtoTranslatedToGrade = converter.mapGradeDtoToGrade(gradeDto);
        verify(gradeRepository).save(gradeDtoTranslatedToGrade);
    }

    @Test
    public void canDeleteGrade(){
        gradeService.deleteById(GRADE_ID);
        verify(gradeRepository).deleteById(GRADE_ID);
    }
}
