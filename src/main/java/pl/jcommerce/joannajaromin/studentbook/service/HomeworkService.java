package pl.jcommerce.joannajaromin.studentbook.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;
import pl.jcommerce.joannajaromin.studentbook.dto.HomeworkDtoWithoutFile;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveHomeworkDto;

import java.util.List;

public interface HomeworkService {

    HomeworkDtoWithoutFile findById(int homeworkId);

    ByteArrayResource downloadFile(int fileId);

    List<HomeworkDtoWithoutFile> findAll();

    void deleteById(int homeworkId);

    HomeworkDtoWithoutFile saveHomework(MultipartFile file, SaveHomeworkDto saveHomeworkDto);
}
