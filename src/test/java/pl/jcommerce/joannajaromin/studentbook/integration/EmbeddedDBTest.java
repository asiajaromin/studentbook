package pl.jcommerce.joannajaromin.studentbook.integration;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@AutoConfigureEmbeddedDatabase
@FlywayTest
@SpringBootTest
public class EmbeddedDBTest {
    @Test
    public void contextLoads() {

    }
}
