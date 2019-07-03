package pl.jcommerce.joannajaromin.studentbook.ServiceTests;

import org.junit.Before;
import org.junit.Test;
import pl.jcommerce.joannajaromin.studentbook.dto.GradeDto;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaGradeConverter;
import pl.jcommerce.joannajaromin.studentbook.entity.Grade;
import pl.jcommerce.joannajaromin.studentbook.repository.GradeRepository;
import pl.jcommerce.joannajaromin.studentbook.service.GradeService;
import pl.jcommerce.joannajaromin.studentbook.service.GradeServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class GradeServiceTest {

    private final int GRADE_ID = 1;
    private List<Grade> gradeList = getGradeList();
    private GradeDto gradeDto;
    private GradeRepository gradeRepository;
    private Grade grade;
    private GradeService gradeService;
    private OrikaGradeConverter gradeConverter;

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
        gradeRepository = mock(GradeRepository.class);
        grade = mock(Grade.class);
        gradeConverter = mock(OrikaGradeConverter.class);
        gradeService = new GradeServiceImpl(gradeRepository,gradeConverter);
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
        Grade gradeDtoTranslatedToGrade = gradeConverter.map(gradeDto, Grade.class);
        verify(gradeRepository).save(gradeDtoTranslatedToGrade);
    }

    @Test
    public void canDeleteGrade(){
        gradeService.deleteById(GRADE_ID);
        verify(gradeRepository).deleteById(GRADE_ID);
    }
}
