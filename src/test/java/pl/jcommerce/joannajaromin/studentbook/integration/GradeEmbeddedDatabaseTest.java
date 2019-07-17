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
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@AutoConfigureEmbeddedDatabase
@FlywayTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GradeEmbeddedDatabaseTest {

    private final int GRADE_ID1 = 1;
    private final int GRADE_ID_FOR_DELETE = 2;
    private final int NON_EXISTANT_GRADE_ID = 21535;
    private final String INCORRECT_GRADE_ID = "62shd";
    private final int STUDENT_ID = 1;
    private final int SUBJECT_ID = 1;
    private final int GRADE = 5;
    private final int INCORRECT_GRADE = 7;
    private final int OK_STATUS_CODE = 200;
    private final int NOT_FOUND_STATUS_CODE = 404;
    private final int BAD_REQUEST_STATUS_CODE = 400;
    private final String GRADE_NOT_FOUND_MESSAGE = "Brak oceny o id = ";
    private final String INCORRECT_ID_FORMAT_MESSAGE = "Nieprawidłowa wartość id.";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void canGetGrade() throws JsonProcessingException {
        var gradeDto = new GradeDto(GRADE_ID1, STUDENT_ID, SUBJECT_ID, GRADE);
        ResponseEntity<String> responseGetEntity = restTemplate.getForEntity("/grades/" + GRADE_ID1, String.class);
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
        assertEquals(STUDENT_ID,dtoJSON.getInt("studentId"));
        assertEquals(SUBJECT_ID,dtoJSON.getInt("subjectId"));
        assertEquals(GRADE,dtoJSON.getInt("grade"));
    }

    @Test
    public void canDeleteGrade() {
        HttpEntity<GradeDto> deleteEntity = new HttpEntity(null);
        ResponseEntity<String> responseDeleteEntity = restTemplate.exchange(("/grades/" + GRADE_ID_FOR_DELETE),
                HttpMethod.DELETE, deleteEntity, String.class);
        int deleteStatusCode = responseDeleteEntity.getStatusCodeValue();
        String expectedDeleteBody = null;
        assertEquals(OK_STATUS_CODE, deleteStatusCode);
        assertEquals(expectedDeleteBody, responseDeleteEntity.getBody());
    }

    @Test
    public void canThrowExceptionForAbsentId() {
        ResponseEntity<String> responseGetAfterDeleteEntity = restTemplate.getForEntity(
                ("/grades/" + NON_EXISTANT_GRADE_ID), String.class);
        int getAfterDeleteStatusCode = responseGetAfterDeleteEntity.getStatusCodeValue();
        assertEquals(NOT_FOUND_STATUS_CODE, getAfterDeleteStatusCode);
        assertTrue(responseGetAfterDeleteEntity.getBody()
                .contains(GRADE_NOT_FOUND_MESSAGE + NON_EXISTANT_GRADE_ID));
    }

    @Test
    public void canValidateGrade() {
        var saveGradeDto = new SaveGradeDto(STUDENT_ID, SUBJECT_ID, INCORRECT_GRADE);
        HttpEntity<SaveGradeDto> entity = new HttpEntity(saveGradeDto);
        ResponseEntity<String> responseEntity = restTemplate.exchange(("/grades"),
                HttpMethod.POST, entity, String.class);
        int statusCode = responseEntity.getStatusCodeValue();
        assertEquals(BAD_REQUEST_STATUS_CODE, statusCode);
    }

    @Test
    public void canReturnMessageForIncorrectId() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                ("/grades/" + INCORRECT_GRADE_ID), String.class);
        int statusCode = responseEntity.getStatusCodeValue();
        assertEquals(NOT_FOUND_STATUS_CODE, statusCode);
        assertEquals(INCORRECT_ID_FORMAT_MESSAGE, responseEntity.getBody());
    }

}
