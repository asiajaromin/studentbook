package pl.jcommerce.joannajaromin.studentbook.ControllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.jcommerce.joannajaromin.studentbook.controller.GradeController;
import pl.jcommerce.joannajaromin.studentbook.dto.GradeDto;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveGradeDto;
import pl.jcommerce.joannajaromin.studentbook.service.GradeService;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GradeController.class)
public class GradeControllerTest {

    private final int GRADE_ID1 = 2;
    private final int GRADE_ID2 = 7;
    private final int STUDENT_ID = 13;
    private final int SUBJECT_ID = 8;
    private final int GRADE1 = 5;
    private final int GRADE2 = 3;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GradeService gradeService;

    @Test
    public void canGetGrade() throws Exception{
        given(this.gradeService.findById(GRADE_ID1))
                .willReturn(new GradeDto(GRADE_ID1,STUDENT_ID,SUBJECT_ID,GRADE1));
        this.mvc.perform(get("/grades/" + GRADE_ID1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(GRADE_ID1))
                .andExpect(jsonPath("$.studentId").value(STUDENT_ID))
                .andExpect(jsonPath("$.subjectId").value(SUBJECT_ID))
                .andExpect(jsonPath("$.grade").value(GRADE1));
    }

    @Test
    public void canGetGradesList() throws Exception{
        var grade1 = new GradeDto(GRADE_ID1,STUDENT_ID,SUBJECT_ID,GRADE1);
        var grade2 = new GradeDto(GRADE_ID2,STUDENT_ID,SUBJECT_ID,GRADE2);
        given(this.gradeService.findAll()).willReturn(Arrays.asList(grade1,grade2));
        this.mvc.perform(get("/grades").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(GRADE_ID1))
                .andExpect(jsonPath("$.[0].studentId").value(STUDENT_ID))
                .andExpect(jsonPath("$.[0].subjectId").value(SUBJECT_ID))
                .andExpect(jsonPath("$.[0].grade").value(GRADE1))
                .andExpect(jsonPath("$.[1].id").value(GRADE_ID2))
                .andExpect(jsonPath("$.[1].studentId").value(STUDENT_ID))
                .andExpect(jsonPath("$.[1].subjectId").value(SUBJECT_ID))
                .andExpect(jsonPath("$.[1].grade").value(GRADE2));
    }

    @Test
    public void canPostGrade() throws Exception{
        SaveGradeDto saveGradeDto = new SaveGradeDto(STUDENT_ID,SUBJECT_ID,GRADE1);
        GradeDto gradeDto = new GradeDto(GRADE_ID1,STUDENT_ID,SUBJECT_ID,GRADE1);
        given(this.gradeService.save(saveGradeDto)).willReturn(gradeDto);
        this.mvc.perform(post("/grades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(saveGradeDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentId").value(STUDENT_ID))
                .andExpect(jsonPath("$.subjectId").value(SUBJECT_ID))
                .andExpect(jsonPath("$.grade").value(GRADE1));
    }

    @Test
    public void test() {
        SaveGradeDto saveGradeDto1 = new SaveGradeDto(STUDENT_ID,SUBJECT_ID,GRADE1);
        SaveGradeDto saveGradeDto2 = new SaveGradeDto(STUDENT_ID,SUBJECT_ID,GRADE1);
        Assert.assertEquals(saveGradeDto1, saveGradeDto2);

    }
}
