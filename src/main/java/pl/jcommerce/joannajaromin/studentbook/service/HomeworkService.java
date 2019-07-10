package pl.jcommerce.joannajaromin.studentbook.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;
import pl.jcommerce.joannajaromin.studentbook.dto.HomeworkDto;
import pl.jcommerce.joannajaromin.studentbook.dto.HomeworkDtoWithoutFile;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveHomeworkDto;

public interface HomeworkService {

    HomeworkDto saveHomework(MultipartFile file, SaveHomeworkDto saveHomeworkDto);

    HomeworkDtoWithoutFile findById(int homeworkId);

    ByteArrayResource downloadFile(int fileId);

}
