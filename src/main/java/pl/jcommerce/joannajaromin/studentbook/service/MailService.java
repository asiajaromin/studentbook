package pl.jcommerce.joannajaromin.studentbook.service;

import pl.jcommerce.joannajaromin.studentbook.dto.EmailDto;

public interface MailService {

    void sendEmail(EmailDto emailDto);
}
