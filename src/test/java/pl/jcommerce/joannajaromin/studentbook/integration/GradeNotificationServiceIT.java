package pl.jcommerce.joannajaromin.studentbook.integration;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.jcommerce.joannajaromin.studentbook.repository.GradeRepository;
import pl.jcommerce.joannajaromin.studentbook.repository.StudentRepository;
import pl.jcommerce.joannajaromin.studentbook.repository.SubjectRepository;
import pl.jcommerce.joannajaromin.studentbook.service.GradeNotificationService;
import pl.jcommerce.joannajaromin.studentbook.service.GradeNotificationServiceImpl;
import pl.jcommerce.joannajaromin.studentbook.service.MailService;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureEmbeddedDatabase
@FlywayTest
@ActiveProfiles("email")
public class GradeNotificationServiceIT {

    public static final int GRADE_ID = 1;

    @TestConfiguration
    static class ContextConfiguration {

        @Bean
        public TaskExecutor taskExecutor() {
            return new SyncTaskExecutor();
        }
    }

    private GreenMail smtpServer;

    @Before
    public void setUp() throws Exception {
        smtpServer = new GreenMail(new ServerSetup(25, null, "smtp"));
        smtpServer.start();
    }

    @After
    public void tearDown() throws Exception {
        smtpServer.stop();
    }

    @Autowired
    private MailService mailService;

    @Autowired
    private GradeRepository gradeRepo;

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private SubjectRepository subjectRepo;

    GradeNotificationService gradeNotificationService = new GradeNotificationServiceImpl(mailService, gradeRepo, studentRepo, subjectRepo);

    @Test
    public void canSendEmail(){
        gradeNotificationService.notifyAboutNewGrade(GRADE_ID);
        var mimeMessages = smtpServer.getReceivedMessages();
        assertEquals(1,mimeMessages.length);
    }

}
