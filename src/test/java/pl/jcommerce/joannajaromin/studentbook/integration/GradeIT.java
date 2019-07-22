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
import org.springframework.http.HttpStatus;
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
public class GradeIT {

    private final int GRADE_ID1 = 1;
    private final int GRADE_ID_FOR_DELETE = 2;
    private final int NON_EXISTENT_GRADE_ID = 21535;
    private final String INCORRECT_GRADE_ID = "62shd";
    private final int STUDENT_ID = 1;
    private final int SUBJECT_ID = 1;
    private final int GRADE = 5;
    private final int INCORRECT_GRADE = 7;
    private final String GRADE_NOT_FOUND_MESSAGE = "Brak oceny o id = ";
    private final String INCORRECT_ID_FORMAT_MESSAGE = "Nieprawidłowa wartość id.";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void canGetGrade() throws JsonProcessingException {
        var gradeDto = new GradeDto(GRADE_ID1, STUDENT_ID, SUBJECT_ID, GRADE);
        ResponseEntity<String> responseGetEntity = restTemplate
                .withBasicAuth("studentbook", "student")
                .getForEntity("/grades/" + GRADE_ID1, String.class);
        String expectedBody = objectMapper.writeValueAsString(gradeDto);
        assertEquals(expectedBody, responseGetEntity.getBody());
        assertEquals(HttpStatus.OK, responseGetEntity.getStatusCode());
    }

    @Test
    public void canPostGrade() throws JSONException {
        var saveGradeDto = new SaveGradeDto(STUDENT_ID, SUBJECT_ID, GRADE);
        HttpEntity<SaveGradeDto> postEntity = new HttpEntity(saveGradeDto);
        ResponseEntity<String> responsePostEntity = restTemplate
                .withBasicAuth("studentbook", "student")
                .exchange(("/grades"), HttpMethod.POST, postEntity, String.class);
        String postBody = responsePostEntity.getBody();
        JSONObject dtoJSON = new JSONObject(postBody);
        assertEquals(HttpStatus.OK, responsePostEntity.getStatusCode());
        assertEquals(STUDENT_ID, dtoJSON.getInt("studentId"));
        assertEquals(SUBJECT_ID, dtoJSON.getInt("subjectId"));
        assertEquals(GRADE, dtoJSON.getInt("grade"));
    }

    @Test
    public void canDeleteGrade() {
        HttpEntity<GradeDto> deleteEntity = new HttpEntity(null);
        ResponseEntity<String> responseDeleteEntity = restTemplate
                .withBasicAuth("studentbook", "student")
                .exchange(("/grades/" + GRADE_ID_FOR_DELETE),
                        HttpMethod.DELETE, deleteEntity, String.class);
        String expectedDeleteBody = null;
        assertEquals(HttpStatus.OK, responseDeleteEntity.getStatusCode());
        assertEquals(expectedDeleteBody, responseDeleteEntity.getBody());
    }

    @Test
    public void canThrowExceptionForAbsentId() {
        ResponseEntity<String> responseEntity = restTemplate
                .withBasicAuth("studentbook", "student")
                .getForEntity(("/grades/" + NON_EXISTENT_GRADE_ID), String.class);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody()
                .contains(GRADE_NOT_FOUND_MESSAGE + NON_EXISTENT_GRADE_ID));
    }

    @Test
    public void cannotSaveInvalidGrade() {
        var saveGradeDto = new SaveGradeDto(STUDENT_ID, SUBJECT_ID, INCORRECT_GRADE);
        HttpEntity<SaveGradeDto> entity = new HttpEntity(saveGradeDto);
        ResponseEntity<String> responseEntity = restTemplate
                .withBasicAuth("studentbook", "student")
                .exchange(("/grades"), HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void canReturnMessageForIncorrectId() {
        ResponseEntity<String> responseEntity = restTemplate
                .withBasicAuth("studentbook", "student")
                .getForEntity(("/grades/" + INCORRECT_GRADE_ID), String.class);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(INCORRECT_ID_FORMAT_MESSAGE, responseEntity.getBody());
    }

    // HttpStatus.FOUND is caused by mvc redirecting to login page
    @Test
    public void unauthorizedUserCannotGetGrade() {
        HttpStatus status = restTemplate
                .getForEntity("/grades/" + GRADE_ID1, String.class)
                .getStatusCode();
        assertEquals(HttpStatus.FOUND, status);
    }

    @Test
    public void unauthorizedUserCannotPostGrade() {
        var saveGradeDto = new SaveGradeDto(STUDENT_ID, SUBJECT_ID, GRADE);
        HttpEntity<SaveGradeDto> postEntity = new HttpEntity(saveGradeDto);
        HttpStatus status = restTemplate
                .exchange(("/grades"), HttpMethod.POST, postEntity, String.class)
                .getStatusCode();
        assertEquals(HttpStatus.FOUND, status);
    }

    @Test
    public void unauthorizedUserCannotDeleteGrade() {
        HttpEntity<GradeDto> deleteEntity = new HttpEntity(null);
        HttpStatus status = restTemplate
                .exchange(("/grades/" + GRADE_ID_FOR_DELETE),
                        HttpMethod.DELETE, deleteEntity, String.class)
                .getStatusCode();
        assertEquals(HttpStatus.FOUND, status);
    }

}
