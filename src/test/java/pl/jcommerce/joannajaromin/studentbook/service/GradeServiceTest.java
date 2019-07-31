package pl.jcommerce.joannajaromin.studentbook.service;

import org.junit.Before;
import org.junit.Test;
import pl.jcommerce.joannajaromin.studentbook.dto.GradeDto;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaGradeConverter;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaSaveGradeConverter;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveGradeDto;
import pl.jcommerce.joannajaromin.studentbook.entity.Grade;
import pl.jcommerce.joannajaromin.studentbook.repository.GradeRepository;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GradeServiceTest {

    private static final int GRADE_ID = 1;
    private static final int STUDENT_ID = 1;
    private static final int SUBJECT_ID = 2;
    private static final int GRADE = 3;
    private GradeDto gradeDto;
    private GradeRepository gradeRepository;
    private Grade grade;
    private SaveGradeDto saveGradeDto;
    private GradeService gradeService;
    private GradeNotificationService gradeNotificationService;
    private OrikaGradeConverter gradeConverter;
    private OrikaSaveGradeConverter saveGradeConverter;

    @Before
    public void before(){
        gradeDto = new GradeDto(GRADE_ID,STUDENT_ID,SUBJECT_ID,GRADE);
        grade = new Grade();
        saveGradeDto = new SaveGradeDto();
        gradeRepository = mock(GradeRepository.class);
        gradeConverter = mock(OrikaGradeConverter.class);
        saveGradeConverter = mock(OrikaSaveGradeConverter.class);
        gradeNotificationService = mock(GradeNotificationService.class);
        gradeService = new GradeServiceImpl(gradeRepository,gradeConverter,saveGradeConverter,gradeNotificationService);
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
        GradeDto savedGradeDto = gradeService.save(saveGradeDto);
        assertEquals(gradeDto,savedGradeDto);
        verify(gradeNotificationService).notifyAboutNewGrade(GRADE_ID);
    }

    @Test
    public void canDeleteGrade(){
        gradeService.deleteById(GRADE_ID);
        verify(gradeRepository).deleteById(GRADE_ID);
    }
}
