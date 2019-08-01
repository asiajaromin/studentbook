//package pl.jcommerce.joannajaromin.studentbook.integration;
//
//import com.icegreen.greenmail.util.GreenMail;
//import com.icegreen.greenmail.util.ServerSetup;
//import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
//import org.flywaydb.test.annotation.FlywayTest;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.task.SyncTaskExecutor;
//import org.springframework.core.task.TaskExecutor;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.test.context.junit4.SpringRunner;
//import pl.jcommerce.joannajaromin.studentbook.dto.SaveGradeDto;
//
//import javax.mail.internet.MimeMessage;
//import java.util.concurrent.ExecutorService;
//
//import static org.junit.Assert.assertEquals;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@RunWith(SpringRunner.class)
//@AutoConfigureEmbeddedDatabase
//@FlywayTest
////@ActiveProfiles("email")
//public class MailIT {
//
//    private static final int EXPECTED_MESSAGES_AMOUNT = 1;
//    private static final int STUDENT_ID = 1;
//    private static final int SUBJECT_ID = 1;
//    private static final int GRADE = 5;
//    private static final String TEACHER_USERNAME = "nauczyciel";
//    private static final String TEACHER_PASSWORD = "nauczyciel321";
//    private static final String SUBJECT_NAME = "Matematyka";
//    private static final String FIRST_NAME = "Jan";
//    private static final String LAST_NAME = "Kowalski";
//    private static final String EXPECTED_EMAIL_SUBJECT = "Wystawiono nową ocenę z przedmiotu: " + SUBJECT_NAME;
//    private static final String HOST = "pop.gmail.com";
//    private static final String STUDENT_EMAIL = "kowalski.jasio.nowy@gmail.com";
//    private static final String STUDENT_PASSWORD = "jasio123";
//    private ExecutorService executorService;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @TestConfiguration
//    static class ContextConfiguration {
//
//        @Bean
//        public TaskExecutor taskExecutor() {
//            return new SyncTaskExecutor();
//        }
//    }
//
//    private GreenMail smtpServer;
//
//    @Before
//    public void setUp() throws Exception {
//        smtpServer = new GreenMail(new ServerSetup(25, null, "smtp"));
//        smtpServer.start();
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        smtpServer.stop();
//    }
//
//    @Ignore
//    @Test
//    public void emailIsSentAfterPostGrade() {
//        var saveGradeDto = new SaveGradeDto(STUDENT_ID, SUBJECT_ID, GRADE);
//        var postEntity = new HttpEntity(saveGradeDto);
//        var response = restTemplate
//                .withBasicAuth(TEACHER_USERNAME, TEACHER_PASSWORD)
//                .exchange(("/grades"), HttpMethod.POST, postEntity, String.class);
//        assertEquals(HttpStatus.OK,response.getStatusCode());
//        MimeMessage[] receivedMessages = smtpServer.getReceivedMessages();
////        MimeMessage mimeMessage = receivedMessages[0];
//        assertEquals(EXPECTED_MESSAGES_AMOUNT,receivedMessages.length);
//    }
//
//}
