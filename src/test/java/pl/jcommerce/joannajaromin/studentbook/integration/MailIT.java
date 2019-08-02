package pl.jcommerce.joannajaromin.studentbook.integration;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveGradeDto;
import pl.jcommerce.joannajaromin.studentbook.util.SmtpServerRule;

import javax.mail.internet.MimeMessage;
import java.util.concurrent.ExecutorService;

import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureEmbeddedDatabase
@FlywayTest
public class MailIT {

    private static final int EXPECTED_MESSAGES_AMOUNT = 1;
    private static final int STUDENT_ID = 1;
    private static final int SUBJECT_ID = 1;
    private static final int GRADE = 5;
    private static final String TEACHER_USERNAME = "nauczyciel";
    private static final String TEACHER_PASSWORD = "nauczyciel321";
    private static final String SUBJECT_NAME = "Matematyka";
    private static final String FIRST_NAME = "Jan";
    private static final String LAST_NAME = "Kowalski";
    private static final String EXPECTED_EMAIL_SUBJECT = "Wystawiono nową ocenę z przedmiotu: " + SUBJECT_NAME;
    private static final String HOST = "pop.gmail.com";
    private static final String STUDENT_EMAIL = "kowalski.jasio.nowy@gmail.com";
    private static final String STUDENT_PASSWORD = "jasio123";
    private ExecutorService executorService;

    @Rule
    public SmtpServerRule smtpServerRule = new SmtpServerRule(2525);

    @Autowired
    private TestRestTemplate restTemplate;

//    @TestConfiguration
//    static class ContextConfiguration {
//
//        @Bean
//        public TaskExecutor taskExecutor() {
//            return new SyncTaskExecutor();
//        }
//    }

    @Test
    public void emailIsSentAfterPostGrade() throws InterruptedException {
        var saveGradeDto = new SaveGradeDto(STUDENT_ID, SUBJECT_ID, GRADE);
        var postEntity = new HttpEntity(saveGradeDto);
        var response = restTemplate
                .withBasicAuth(TEACHER_USERNAME, TEACHER_PASSWORD)
                .exchange(("/grades"), HttpMethod.POST, postEntity, String.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        Thread.sleep(5000);
        MimeMessage[] receivedMessages = smtpServerRule.getMessages();
//        MimeMessage mimeMessage = receivedMessages[0];
        assertEquals(EXPECTED_MESSAGES_AMOUNT,receivedMessages.length);
    }

}
