package pl.jcommerce.joannajaromin.studentbook.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.test.annotation.FlywayTest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import pl.jcommerce.joannajaromin.studentbook.dto.HomeworkDto;
import pl.jcommerce.joannajaromin.studentbook.dto.HomeworkDtoWithoutFile;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveHomeworkDto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@AutoConfigureEmbeddedDatabase
@FlywayTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class HomeworkEmbeddedDBTest {

    private final int GROUP_ID = 2;
    private final int TEACHER_ID = 2;
    private final int SUBJECT_ID = 2;
    private final int GET_ID = 2;
    private final int NON_EXISTENT_HOMEWORK_ID = 532;
    private final String INCORRECT_HOMEWORK_ID = "63732743743";
    private final int DELETE_ID = 1;
    private final String POST_FILE_NAME = "Zadanie123.pdf";
    private final String TOO_LONG_FILE_NAME = "Nazwa pliku przekraczająca dopuszczalną długość wynoszącą 50 znaków.";
    private final String POST_FILE_DESCRIPTION = "Bardzo szybkie i łatwe zadanie domowe.";
    private final String FILE_NAME = "Zadanie.pdf";
    private final String FILE_DESCRIPTION = "Zadanie dla klasy 2B";
    private final int OK_STATUS_CODE = 200;
    private final int NOT_FOUND_STATUS_CODE = 404;
    private final int BAD_REQUEST_STATUS_CODE = 400;
    private final String HOMEWORK_NOT_FOUND_MESSAGE = "Brak zadania o id = ";
    private final String INCORRECT_ID_FORMAT_MESSAGE = "Nieprawidłowa wartość id.";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void canGetHomeworkWithoutFile() throws JsonProcessingException {
        var homeworkDto = new HomeworkDtoWithoutFile(GET_ID,GROUP_ID,TEACHER_ID,SUBJECT_ID,
                FILE_NAME,FILE_DESCRIPTION);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(("/homeworks/"+GET_ID),
                String.class);
        String expectedBody = objectMapper.writeValueAsString(homeworkDto);
        assertEquals(expectedBody,responseEntity.getBody());
        assertEquals(OK_STATUS_CODE,responseEntity.getStatusCodeValue());
    }

    @Test
    public void canGetHomeworksList() throws JSONException {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(("/homeworks"),
                String.class);
        JSONArray jsonArray = new JSONArray(responseEntity.getBody());
        List<String> jsonList = new ArrayList();
        for (var i = 0; i < jsonArray.length(); i++){
            jsonList.add(jsonArray.getString(i));
        }
        assertEquals(OK_STATUS_CODE,responseEntity.getStatusCodeValue());
        assertTrue(jsonList.size()>0);
    }

    @Test
    public void canDeleteHomework(){
        HttpEntity<HomeworkDto> entity = new HttpEntity(null);
        ResponseEntity<String> responseEntity = restTemplate.exchange(("/homeworks/" + DELETE_ID),
                HttpMethod.DELETE, entity, String.class);
        String expectedDeleteBody = null;
        assertEquals(OK_STATUS_CODE, responseEntity.getStatusCodeValue());
        assertEquals(expectedDeleteBody, responseEntity.getBody());
    }

    @Test
    public void canPostHomework() throws JSONException {
        var saveHomeworkDto = new SaveHomeworkDto(GROUP_ID,TEACHER_ID, SUBJECT_ID, POST_FILE_NAME,
                POST_FILE_DESCRIPTION);
        ClassPathResource file = new ClassPathResource("Zadanie123.pdf");
        LinkedMultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
        parameters.add("uploadFile", file);
        parameters.add("saveHomeworkDto", saveHomeworkDto);
        HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<>(parameters);
        ResponseEntity<String> responseEntity = restTemplate.exchange(("/homeworks"), HttpMethod.POST, entity, String.class);
        JSONObject dtoJSON = new JSONObject(responseEntity.getBody());
        assertEquals(OK_STATUS_CODE, responseEntity.getStatusCodeValue());
        assertEquals(POST_FILE_NAME,dtoJSON.getString("fileName"));
        assertEquals(POST_FILE_DESCRIPTION,dtoJSON.getString("fileDescription"));
    }

    @Test
    public void canThrowExceptionForAbsentId() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                ("/homeworks/" + NON_EXISTENT_HOMEWORK_ID), String.class);
        assertEquals(NOT_FOUND_STATUS_CODE, responseEntity.getStatusCodeValue());
        assertTrue(responseEntity.getBody()
                .contains(HOMEWORK_NOT_FOUND_MESSAGE + NON_EXISTENT_HOMEWORK_ID));
    }

    @Test
    public void canReturnMessageForIncorrectId() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                ("/homeworks/" + INCORRECT_HOMEWORK_ID), String.class);
        int statusCode = responseEntity.getStatusCodeValue();
        assertEquals(NOT_FOUND_STATUS_CODE, statusCode);
        assertEquals(INCORRECT_ID_FORMAT_MESSAGE, responseEntity.getBody());
    }

    @Test
    public void canValidateFileName() {
        var saveHomeworkDto = new SaveHomeworkDto(GROUP_ID,TEACHER_ID, SUBJECT_ID, TOO_LONG_FILE_NAME,
                POST_FILE_DESCRIPTION);
        ClassPathResource file = new ClassPathResource("Zadanie123.pdf");
        LinkedMultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
        parameters.add("uploadFile", file);
        parameters.add("saveHomeworkDto", saveHomeworkDto);
        HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<>(parameters);
        ResponseEntity<String> responseEntity = restTemplate.exchange(("/homeworks"), HttpMethod.POST, entity, String.class);
        assertEquals(BAD_REQUEST_STATUS_CODE, responseEntity.getStatusCodeValue());
    }

}
