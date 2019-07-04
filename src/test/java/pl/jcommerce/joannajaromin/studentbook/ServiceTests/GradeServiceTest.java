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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class GradeServiceTest {

    private final int GRADE_ID = 1;
    private GradeDto gradeDto;
    private GradeRepository gradeRepository;
    private Grade grade;
    private GradeService gradeService;
    private OrikaGradeConverter gradeConverter;
    List<Grade> gradeList;
    List<GradeDto> gradeDtoList;

    @Before
    public void before(){
        gradeDto = new GradeDto();
        grade = new Grade();
        gradeRepository = mock(GradeRepository.class);
        gradeConverter = mock(OrikaGradeConverter.class);
        gradeService = new GradeServiceImpl(gradeRepository,gradeConverter);
        gradeList = createGradeList(grade);
        gradeDtoList = createGradeDtoList(gradeDto);
        when(gradeRepository.findById(GRADE_ID)).thenReturn(grade);
        when(gradeRepository.findAll()).thenReturn(gradeList);
        when(gradeRepository.save(grade)).thenReturn(grade);
        when(gradeConverter.mapAsList(gradeList,GradeDto.class)).thenReturn(gradeDtoList);
        when(gradeConverter.map(grade,GradeDto.class)).thenReturn(gradeDto);
        when(gradeConverter.map(gradeDto,Grade.class)).thenReturn(grade);
    }

    private List<Grade> createGradeList(Grade grade) {
        gradeList = new ArrayList<>();
        gradeList.add(grade);
        gradeList.add(grade);
        return gradeList;
    }

    private List<GradeDto> createGradeDtoList(GradeDto grade) {
        gradeDtoList = new ArrayList<>();
        gradeDtoList.add(grade);
        gradeDtoList.add(grade);
        return gradeDtoList;
    }

    @Test
    public void canGetGradesList(){
        List<GradeDto> obtainedDtoList = gradeService.findAll();
        assertEquals(gradeDtoList,obtainedDtoList);
    }

    @Test
    public void canGetSingleGrade(){
        GradeDto obtainedDto = gradeService.findById(GRADE_ID);
        assertEquals(gradeDto,obtainedDto);
    }

    @Test
    public void canSaveGrade(){
        GradeDto savedGradeDto = gradeService.save(gradeDto);
        assertEquals(gradeDto,savedGradeDto);
    }

    @Test
    public void canDeleteGrade(){
        gradeService.deleteById(GRADE_ID);
        verify(gradeRepository).deleteById(GRADE_ID);
    }
}
