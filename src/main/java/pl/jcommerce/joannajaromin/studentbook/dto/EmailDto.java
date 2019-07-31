package pl.jcommerce.joannajaromin.studentbook.dto;

import lombok.Data;
import org.thymeleaf.context.Context;

@Data
public class EmailDto {
    private final String recipient;
    private final String subject;
    private final String templateName;
    private final Context context;
}
