package pl.jcommerce.joannajaromin.studentbook.ServiceTests;

import org.junit.Before;
import org.junit.Test;
import pl.jcommerce.joannajaromin.studentbook.dto.GradeDto;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaGradeConverter;
import pl.jcommerce.joannajaromin.studentbook.entity.Grade;
import pl.jcommerce.joannajaromin.studentbook.repository.GradeRepository;
import pl.jcommerce.joannajaromin.studentbook.service.GradeService;
import pl.jcommerce.joannajaromin.studentbook.service.GradeServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class GradeServiceTest {

    private final int GRADE_ID = 1;
    private GradeDto gradeDto;
    private GradeRepository gradeRepository;
    private Grade grade;
    private GradeService gradeService;
    private OrikaGradeConverter gradeConverter;

    @Before
    public void before(){
        gradeDto = new GradeDto();
        grade = new Grade();
        gradeRepository = mock(GradeRepository.class);
        gradeConverter = mock(OrikaGradeConverter.class);
        gradeService = new GradeServiceImpl(gradeRepository,gradeConverter);
        when(gradeConverter.map(grade,GradeDto.class)).thenReturn(gradeDto);
    }

    @Test
    public void canGetGradesList(){
        List<Grade> gradeList = Arrays.asList(grade,grade);
        List<GradeDto> gradeDtoList = Arrays.asList(gradeDto,gradeDto);
        when(gradeRepository.findAll()).thenReturn(gradeList);
        when(gradeConverter.mapAsList(gradeList,GradeDto.class)).thenReturn(gradeDtoList);
        List<GradeDto> obtainedDtoList = gradeService.findAll();
        assertEquals(gradeDtoList,obtainedDtoList);
    }

    @Test
    public void canGetSingleGrade(){
        when(gradeRepository.findById(GRADE_ID)).thenReturn(grade);
        GradeDto obtainedDto = gradeService.findById(GRADE_ID);
        assertEquals(gradeDto,obtainedDto);
    }

    @Test
    public void canSaveGrade(){
        when(gradeConverter.map(gradeDto,Grade.class)).thenReturn(grade);
        when(gradeRepository.save(grade)).thenReturn(grade);
        GradeDto savedGradeDto = gradeService.save(gradeDto);
        assertEquals(gradeDto,savedGradeDto);
    }

    @Test
    public void canDeleteGrade(){
        gradeService.deleteById(GRADE_ID);
        verify(gradeRepository).deleteById(GRADE_ID);
    }
}
