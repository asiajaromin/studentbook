package pl.jcommerce.joannajaromin.studentbook.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import pl.jcommerce.joannajaromin.studentbook.dto.GradeDto;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveGradeDto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GradeAllLayerTest {

    private final int STUDENT_ID = 2;
    private final int SUBJECT_ID = 4;
    private final int GRADE1 = 5;
    private final int INCORRECT_GRADE = 8;
    private final String INCORRECT_GRADE_ID = "x7k3v64k";
    private final int OK_STATUS_CODE = 200;
    private final int NOT_FOUND_STATUS_CODE = 404;
    private final int BAD_REQUEST_STATUS_CODE = 400;
    private final String GRADE_NOT_FOUND_MESSAGE = "Nie znaleziono oceny";
    private final String INCORRECT_ID_FORMAT_MESSAGE = "Nieprawidłowa wartość id.";

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;
    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    // can all this be in one method?
    // it can be split into 4 separate tests, but then I would have to put post method into @Before
    // to obtain gradeId
    public void canPostGetAndDeleteGrade() throws Exception {
        var saveGradeDto = new SaveGradeDto(STUDENT_ID, SUBJECT_ID, GRADE1);
        HttpEntity<SaveGradeDto> postEntity = new HttpEntity(saveGradeDto, headers);
        ResponseEntity<String> responsePostEntity = restTemplate.exchange(
                createURLWithPort("/grades"),
                HttpMethod.POST, postEntity, String.class);
        String postBody = responsePostEntity.getBody();
        int gradeId = new JSONObject(postBody).getInt("id");
        int postStatusCode = responsePostEntity.getStatusCodeValue();
        assertEquals(OK_STATUS_CODE, postStatusCode);

        var gradeDto = new GradeDto(gradeId, STUDENT_ID, SUBJECT_ID, GRADE1);
        HttpEntity<GradeDto> getAndDeleteEntity = new HttpEntity(null, headers);
        ResponseEntity<String> responseGetEntity = restTemplate.exchange(
                createURLWithPort("/grades/"+gradeId),
                HttpMethod.GET, getAndDeleteEntity, String.class);
        int getStatusCode = responseGetEntity.getStatusCodeValue();
        String expectedBody = objectMapper.writeValueAsString(gradeDto);
        assertEquals(expectedBody,responseGetEntity.getBody());
        assertEquals(OK_STATUS_CODE,getStatusCode);

        ResponseEntity<String> responseDeleteEntity = restTemplate.exchange(
                createURLWithPort("/grades/" + gradeId),
                HttpMethod.DELETE, getAndDeleteEntity, String.class);
        int deleteStatusCode = responseDeleteEntity.getStatusCodeValue();
        String expectedDeleteBody = null;
        assertEquals(OK_STATUS_CODE,deleteStatusCode);
        assertEquals(expectedDeleteBody,responseDeleteEntity.getBody());

        ResponseEntity<String> responseGetAfterDeleteEntity = restTemplate.exchange(
                createURLWithPort("/grades/"+gradeId),
                HttpMethod.GET, getAndDeleteEntity, String.class);
        int getAfterDeleteStatusCode = responseGetAfterDeleteEntity.getStatusCodeValue();
        assertEquals(NOT_FOUND_STATUS_CODE,getAfterDeleteStatusCode);
        assertTrue(responseGetAfterDeleteEntity.getBody().contains(GRADE_NOT_FOUND_MESSAGE));
    }

    @Test
    public void gradeIsValidated(){
        var saveGradeDto = new SaveGradeDto(STUDENT_ID, SUBJECT_ID, INCORRECT_GRADE);
        HttpEntity<SaveGradeDto> entity = new HttpEntity(saveGradeDto, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                createURLWithPort("/grades"),
                HttpMethod.POST, entity, String.class);
        int statusCode = responseEntity.getStatusCodeValue();
        assertEquals(BAD_REQUEST_STATUS_CODE, statusCode);
    }

    @Test
    public void incorrectGradeIdFormatReturnsMessage(){
        HttpEntity<GradeDto> entity = new HttpEntity(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                createURLWithPort("/grades/"+INCORRECT_GRADE_ID),
                HttpMethod.GET, entity, String.class);
        int statusCode = responseEntity.getStatusCodeValue();
        assertEquals(NOT_FOUND_STATUS_CODE,statusCode);
        assertEquals(INCORRECT_ID_FORMAT_MESSAGE,responseEntity.getBody());
    }

}
