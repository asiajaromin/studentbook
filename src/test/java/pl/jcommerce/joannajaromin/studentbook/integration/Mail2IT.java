package pl.jcommerce.joannajaromin.studentbook.integration;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.context.Context;
import pl.jcommerce.joannajaromin.studentbook.dto.EmailDto;
import pl.jcommerce.joannajaromin.studentbook.service.MailService;
import pl.jcommerce.joannajaromin.studentbook.util.SmtpServerRule;

import javax.mail.internet.MimeMessage;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureEmbeddedDatabase
@FlywayTest
public class Mail2IT {
    @Rule
    public SmtpServerRule smtpServerRule = new SmtpServerRule(2525);

    @Autowired
    private MailService mailService;

    @TestConfiguration
    static class ContextConfiguration {

        @Bean
        public TaskExecutor taskExecutor() {
            return new SyncTaskExecutor();
        }
    }

    @Test
    public void test() throws Exception {
        Context context = new Context();
        EmailDto emailDto = new EmailDto("a@a.pl", "Test", "grade-notification-template", context);
        mailService.sendEmail(emailDto);

        MimeMessage[] receivedMessages = smtpServerRule.getMessages();
        assertEquals(1, receivedMessages.length);

        MimeMessage current = receivedMessages[0];

        assertEquals("Test", current.getSubject());
        assertEquals("a@a.pl", current.getAllRecipients()[0].toString());

    }

}
