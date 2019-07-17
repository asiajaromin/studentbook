package pl.jcommerce.joannajaromin.studentbook.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.flywaydb.test.annotation.FlywayTest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import pl.jcommerce.joannajaromin.studentbook.dto.GradeDto;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveGradeDto;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@AutoConfigureEmbeddedDatabase
@FlywayTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GradeEmbeddedDatabaseTest {

    private final int GRADE_ID = 1;
    private final int STUDENT_ID = 2;
    private final int SUBJECT_ID = 2;
    private final int GRADE = 2;
    private final int OK_STATUS_CODE = 200;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void canGetGrade() throws JsonProcessingException {
        var gradeDto = new GradeDto(GRADE_ID, STUDENT_ID, SUBJECT_ID, GRADE);
        ResponseEntity<String> responseGetEntity = restTemplate.getForEntity("/grades/" + GRADE_ID, String.class);
        int getStatusCode = responseGetEntity.getStatusCodeValue();
        String expectedBody = objectMapper.writeValueAsString(gradeDto);
        assertEquals(expectedBody, responseGetEntity.getBody());
        assertEquals(OK_STATUS_CODE, getStatusCode);
    }

    @Test
    public void canPostGrade() throws JSONException {
        var saveGradeDto = new SaveGradeDto(STUDENT_ID, SUBJECT_ID, GRADE);
        HttpEntity<SaveGradeDto> postEntity = new HttpEntity(saveGradeDto);
        ResponseEntity<String> responsePostEntity = restTemplate.exchange(("/grades"),
                HttpMethod.POST, postEntity, String.class);
        String postBody = responsePostEntity.getBody();
        JSONObject dtoJSON = new JSONObject(postBody);
        int postStatusCode = responsePostEntity.getStatusCodeValue();
        assertEquals(OK_STATUS_CODE, postStatusCode);
        assertEquals(GRADE_ID,dtoJSON.getInt("id"));
        assertEquals(STUDENT_ID,dtoJSON.getInt("studentId"));
        assertEquals(SUBJECT_ID,dtoJSON.getInt("subjectId"));
        assertEquals(GRADE,dtoJSON.getInt("grade"));
    }
}
