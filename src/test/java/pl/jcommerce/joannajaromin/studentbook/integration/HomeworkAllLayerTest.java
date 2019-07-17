package pl.jcommerce.joannajaromin.studentbook.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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
import pl.jcommerce.joannajaromin.studentbook.dto.HomeworkDtoWithoutFile;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveHomeworkDto;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@AutoConfigureEmbeddedDatabase
@FlywayTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class HomeworkAllLayerTest {

    private final int GROUP_ID = 2;
    private final int TEACHER_ID = 2;
    private final int SUBJECT_ID = 2;
    private final int GET_ID = 2;
    private final int DELETE_ID = 1;
    private final String POST_FILE_NAME = "Zadanie123";
    private final String POST_FILE_DESCRIPTION = "Bardzo szybkie i łatwe zadanie domowe.";
    private final String POT_FILE_CONTENT_STRING = "Napisz ładne wypracowanie na dowoly temat.";
    private final byte[] POST_FILE_CONTENT = POT_FILE_CONTENT_STRING.getBytes();
    private final String FILE_NAME = "Zadanie.pdf";
    private final String FILE_DESCRIPTION = "Zadanie dla klasy 2B";
    private final int OK_STATUS_CODE = 200;
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
        int statusCode = responseEntity.getStatusCodeValue();
        String expectedBody = objectMapper.writeValueAsString(homeworkDto);
        assertEquals(expectedBody,responseEntity.getBody());
        assertEquals(OK_STATUS_CODE,statusCode);
    }

    @Test
    public void canDeleteHomework(){
        HttpEntity<GradeDto> entity = new HttpEntity(null);
        ResponseEntity<String> responseEntity = restTemplate.exchange(("/grades/" + DELETE_ID),
                HttpMethod.DELETE, entity, String.class);
        String expectedDeleteBody = null;
        assertEquals(OK_STATUS_CODE, responseEntity.getStatusCodeValue());
        assertEquals(expectedDeleteBody, responseEntity.getBody());
    }

    @Test
    public void canPostHomework() throws JSONException, IOException {
        var saveHomeworkDto = new SaveHomeworkDto(GROUP_ID,TEACHER_ID,SUBJECT_ID,POST_FILE_NAME,POST_FILE_DESCRIPTION);
        String saveHomeworkJSON = objectMapper.writeValueAsString(saveHomeworkDto);
        File file = new File(FILE_NAME,POT_FILE_CONTENT_STRING);
        FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);
        StringBody stringBody = new StringBody(saveHomeworkJSON,ContentType.APPLICATION_JSON);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addBinaryBody("file",POST_FILE_CONTENT);
        builder.addTextBody("saveHomeworkDto",saveHomeworkJSON);
        org.apache.http.HttpEntity httpEntity = builder.build();
        HttpPost post = new HttpPost("/homeworks");
        post.setEntity(httpEntity);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpResponse response = client.execute(post);
        org.apache.http.HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity,"UTF-8");
        HttpEntity<MultipartEntityBuilder> entity2 = new HttpEntity(httpEntity);
        ResponseEntity<String> responseEntity = restTemplate.exchange(("/homeworks"),
                HttpMethod.POST, entity2, String.class);
        String postBody = responseEntity.getBody();
        log.info("Przekształcanie obiektu {}",responseString);
        log.info("Status code: {}, response.getStatusLine().getStatusCode()");
        JSONObject jsonObject = new JSONObject(responseString);
    }


}
