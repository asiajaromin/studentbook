package pl.jcommerce.joannajaromin.studentbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class StudentbookApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentbookApplication.class, args);
    }

}
