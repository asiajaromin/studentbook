package pl.jcommerce.joannajaromin.studentbook.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import pl.jcommerce.joannajaromin.studentbook.dto.HomeworkDtoWithoutFile;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveHomeworkDto;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class HomeworkAllLayerTest {

    private final int GROUP_ID = 2;
    private final int TEACHER_ID = 1;
    private final int SUBJECT_ID = 4;
    private final String FILE_NAME = "Zadanie123";
    private final String FILE_DESCRIPTION = "Bardzo szybkie i łatwe zadanie domowe.";
    private final String FILE_CONTENT_STRING = "Napisz ładne wypracowanie na dowoly temat.";
    private final byte[] FILE_CONTENT = FILE_CONTENT_STRING.getBytes();
    private final int OK_STATUS_CODE = 200;
    private Integer homeworkId;
    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;
    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Before
    public void before() throws JSONException, IOException {
        var saveHomeworkDto = new SaveHomeworkDto(GROUP_ID,TEACHER_ID,SUBJECT_ID,FILE_NAME,FILE_DESCRIPTION);
        String saveHomeworkJSON = objectMapper.writeValueAsString(saveHomeworkDto);
//        File file = new File(FILE_NAME,FILE_CONTENT_STRING);
//        FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);
//        StringBody stringBody = new StringBody(saveHomeworkJSON,ContentType.APPLICATION_JSON);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addBinaryBody("file",FILE_CONTENT);
        builder.addTextBody("saveHomeworkDto",saveHomeworkJSON);
        org.apache.http.HttpEntity httpEntity = builder.build();
        String uri = createURLWithPort("/homeworks");
        HttpPost post = new HttpPost(uri);
        post.setEntity(httpEntity);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpResponse response = client.execute(post);
        org.apache.http.HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity,"UTF-8");
//        HttpEntity<MultipartEntityBuilder> entity = new HttpEntity(httpEntity, headers);
//        ResponseEntity<String> responseEntity = restTemplate.exchange(
//                createURLWithPort("/homeworks"),
//                HttpMethod.POST, entity, String.class);
//        String postBody = responseEntity.getBody();
        log.info("Przekształcanie obiektu {}",responseString);
        log.info("Status code: {}, response.getStatusLine().getStatusCode());
        JSONObject jsonObject = new JSONObject(responseString);
        homeworkId = jsonObject.getInt("id");
    }

    @Test
    public void canGetHomeworkWithoutFile() throws JsonProcessingException {
        var homeworkDto = new HomeworkDtoWithoutFile(homeworkId,GROUP_ID,TEACHER_ID,SUBJECT_ID,
                FILE_NAME,FILE_DESCRIPTION);
        HttpEntity<HomeworkDtoWithoutFile> entity = new HttpEntity(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                createURLWithPort("/homeworks/"+homeworkId),
                HttpMethod.GET, entity, String.class);
        int statusCode = responseEntity.getStatusCodeValue();
        String expectedBody = objectMapper.writeValueAsString(homeworkDto);
        assertEquals(expectedBody,responseEntity.getBody());
        assertEquals(OK_STATUS_CODE,statusCode);
    }



}
