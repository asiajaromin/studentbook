package pl.jcommerce.joannajaromin.studentbook.AllLayerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveGradeDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GradeAllLayerTest {

    private final int STUDENT_ID = 2;
    private final int SUBJECT_ID = 4;
    private final int GRADE1 = 5;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void canPostGetAndDeleteGrade() throws Exception{
        var saveGradeDto = new SaveGradeDto(STUDENT_ID,SUBJECT_ID,GRADE1);
        var response = mockMvc.perform(post("/grades")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(saveGradeDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        var jsonObject = new JSONObject(response);
        int gradeId = jsonObject.getInt("id");

        this.mockMvc.perform(get("/grades/" + gradeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(gradeId))
                .andExpect(jsonPath("$.studentId").value(STUDENT_ID))
                .andExpect(jsonPath("$.subjectId").value(SUBJECT_ID))
                .andExpect(jsonPath("$.grade").value(GRADE1));

        this.mockMvc.perform(delete("/grades/" + gradeId))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/grades/" + gradeId))
                // status should not be 200 - test needs to be changed after implementing exception handling
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist());
    }

}
