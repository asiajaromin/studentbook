package pl.jcommerce.joannajaromin.studentbook.integration;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveGradeDto;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@AutoConfigureEmbeddedDatabase
@FlywayTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmailIT {

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
    private final String EXPECTED_EMAIL_TEXT = prepareEmailText();

    @Autowired
    private TestRestTemplate restTemplate;

    private String prepareEmailText(){
        return new StringBuilder()
                .append("Witaj ")
                .append(FIRST_NAME)
                .append(" ")
                .append(LAST_NAME)
                .append(System.lineSeparator())
                .append(System.lineSeparator())
                .append("Otrzymałeś ocenę!")
                .append(System.lineSeparator())
                .append("Nazwa przedmiotu: ")
                .append(SUBJECT_NAME)
                .append(System.lineSeparator())
                .append("Ocena: ")
                .append(GRADE)
                .append(System.lineSeparator())
                .toString();
    }

    @Test
    public void emailIsSentAfterPostGrade() throws MessagingException, IOException {
        var saveGradeDto = new SaveGradeDto(STUDENT_ID, SUBJECT_ID, GRADE);
        var postEntity = new HttpEntity(saveGradeDto);
        restTemplate
                .withBasicAuth(TEACHER_USERNAME, TEACHER_PASSWORD)
                .exchange(("/grades"), HttpMethod.POST, postEntity, String.class);
        Message lastMessage;
        Properties properties = new Properties();
        properties.put("mail.pop3.host", HOST);
        properties.put("mail.pop3.port", "995");
        properties.put("mail.pop3.starttls.enable", "true");
        Session emailSession = Session.getDefaultInstance(properties);
        Store store = emailSession.getStore("pop3s");
        store.connect(HOST, STUDENT_EMAIL, STUDENT_PASSWORD);
        Folder emailFolder = store.getFolder("INBOX");
        emailFolder.open(Folder.READ_ONLY);
        Message[] messages = emailFolder.getMessages();
        lastMessage = messages[messages.length-1];
        assertEquals(EXPECTED_EMAIL_SUBJECT,lastMessage.getSubject());
        assertEquals(EXPECTED_EMAIL_TEXT,lastMessage.getContent().toString());
        emailFolder.close(false);
        store.close();
    }

}
