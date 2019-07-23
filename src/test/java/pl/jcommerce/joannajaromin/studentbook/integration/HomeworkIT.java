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
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import pl.jcommerce.joannajaromin.studentbook.dto.HomeworkDto;
import pl.jcommerce.joannajaromin.studentbook.dto.HomeworkDtoWithoutFile;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveHomeworkDto;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@AutoConfigureEmbeddedDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class HomeworkIT {

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
    private final String HOMEWORK_NOT_FOUND_MESSAGE = "Brak zadania o id = ";
    private final String INCORRECT_ID_FORMAT_MESSAGE = "Nieprawidłowa wartość id.";
    private final int ARRAY_LENGTH = 2;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @FlywayTest
    public void canGetHomeworkWithoutFile() throws JsonProcessingException {
        var homeworkDto = new HomeworkDtoWithoutFile(GET_ID, GROUP_ID, TEACHER_ID, SUBJECT_ID,
                FILE_NAME, FILE_DESCRIPTION);
        ResponseEntity<String> responseEntity = restTemplate
                .withBasicAuth("studentbook", "student")
                .getForEntity(("/homeworks/" + GET_ID), String.class);
        String expectedBody = objectMapper.writeValueAsString(homeworkDto);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedBody, responseEntity.getBody());
    }

    @Test
    @FlywayTest
    public void canGetHomeworksList() throws JSONException, JsonProcessingException {
        ResponseEntity<String> responseEntity = restTemplate
                .withBasicAuth("studentbook", "student")
                .getForEntity(("/homeworks"), String.class);
        var dto = new HomeworkDtoWithoutFile(GET_ID, GROUP_ID, TEACHER_ID, SUBJECT_ID, FILE_NAME,
                FILE_DESCRIPTION);
        String expectedJson = objectMapper.writeValueAsString(dto);
        JSONArray jsonArray = new JSONArray(responseEntity.getBody());
        List<String> jsonList = IntStream.range(0, jsonArray.length())
                .mapToObj(i -> {
                    try {
                        return jsonArray.getString(i);
                    } catch (JSONException e) {
                        return "Empty list";
                    }
                }).collect(Collectors.toList());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        JSONAssert.assertEquals(expectedJson, jsonArray.getString(1), true);
        assertEquals(ARRAY_LENGTH, jsonArray.length());
        assertEquals(ARRAY_LENGTH, jsonList.size());
    }

    @Test
    @FlywayTest
    public void canDeleteHomework() {
        HttpEntity<HomeworkDto> entity = new HttpEntity(null);
        ResponseEntity<String> responseEntity = restTemplate
                .withBasicAuth("studentbook", "student")
                .exchange(("/homeworks/" + DELETE_ID), HttpMethod.DELETE, entity, String.class);
        String expectedDeleteBody = null;
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedDeleteBody, responseEntity.getBody());
    }

    @Test
    @FlywayTest
    public void canPostHomework() throws JSONException {
        var saveHomeworkDto = new SaveHomeworkDto(GROUP_ID, TEACHER_ID, SUBJECT_ID, POST_FILE_NAME,
                POST_FILE_DESCRIPTION);
        ClassPathResource file = new ClassPathResource("Zadanie123.pdf");
        LinkedMultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
        parameters.add("uploadFile", file);
        parameters.add("saveHomeworkDto", saveHomeworkDto);
        HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<>(parameters);
        ResponseEntity<String> responseEntity = restTemplate
                .withBasicAuth("studentbook", "student")
                .exchange(("/homeworks"), HttpMethod.POST, entity, String.class);
        JSONObject dtoJSON = new JSONObject(responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(POST_FILE_NAME, dtoJSON.getString("fileName"));
        assertEquals(POST_FILE_DESCRIPTION, dtoJSON.getString("fileDescription"));
    }

    @Test
    @FlywayTest
    public void canGetFileContent() throws JSONException, IOException {
        var saveHomeworkDto = new SaveHomeworkDto(GROUP_ID, TEACHER_ID, SUBJECT_ID, POST_FILE_NAME,
                POST_FILE_DESCRIPTION);
        ClassPathResource file = new ClassPathResource("Zadanie123.pdf");
        LinkedMultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
        parameters.add("uploadFile", file);
        parameters.add("saveHomeworkDto", saveHomeworkDto);
        HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<>(parameters);
        ResponseEntity<String> responseEntity = restTemplate
                .withBasicAuth("studentbook", "student")
                .exchange(("/homeworks"), HttpMethod.POST, entity, String.class);
        JSONObject dtoJSON = new JSONObject(responseEntity.getBody());
        int homeworkId = dtoJSON.getInt("id");
        ResponseEntity<ByteArrayResource> fileEntity = restTemplate
                .withBasicAuth("studentbook", "student")
                .getForEntity("/homeworks/fileContent/" + homeworkId, ByteArrayResource.class);
        ByteArrayResource resource = fileEntity.getBody();
        ByteArrayResource expectedFile = new ByteArrayResource(file.getInputStream().readAllBytes());
        assertEquals(HttpStatus.OK, fileEntity.getStatusCode());
        assertEquals(expectedFile, resource);
    }

    @Test
    @FlywayTest
    public void canThrowExceptionForAbsentId() {
        ResponseEntity<String> responseEntity = restTemplate
                .withBasicAuth("studentbook", "student")
                .getForEntity(("/homeworks/" + NON_EXISTENT_HOMEWORK_ID), String.class);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody()
                .contains(HOMEWORK_NOT_FOUND_MESSAGE + NON_EXISTENT_HOMEWORK_ID));
    }

    @Test
    @FlywayTest
    public void canReturnMessageForIncorrectId() {
        ResponseEntity<String> responseEntity = restTemplate
                .withBasicAuth("studentbook", "student")
                .getForEntity(("/homeworks/" + INCORRECT_HOMEWORK_ID), String.class);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(INCORRECT_ID_FORMAT_MESSAGE, responseEntity.getBody());
    }

    @Test
    @FlywayTest
    public void cannotSaveInvalidFileName() {
        var saveHomeworkDto = new SaveHomeworkDto(GROUP_ID, TEACHER_ID, SUBJECT_ID, TOO_LONG_FILE_NAME,
                POST_FILE_DESCRIPTION);
        ClassPathResource file = new ClassPathResource("Zadanie123.pdf");
        LinkedMultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
        parameters.add("uploadFile", file);
        parameters.add("saveHomeworkDto", saveHomeworkDto);
        HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<>(parameters);
        ResponseEntity<String> responseEntity = restTemplate
                .withBasicAuth("studentbook", "student")
                .exchange(("/homeworks"), HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    @FlywayTest
    public void unauthorizedUserCannotGetHomework() {
        HttpStatus status = restTemplate
                .getForEntity(("/homeworks/" + GET_ID), String.class).getStatusCode();
        assertEquals(HttpStatus.UNAUTHORIZED, status);
    }

}
