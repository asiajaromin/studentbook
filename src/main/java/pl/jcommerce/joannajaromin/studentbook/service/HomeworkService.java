package pl.jcommerce.joannajaromin.studentbook.service;

import org.springframework.web.multipart.MultipartFile;
import pl.jcommerce.joannajaromin.studentbook.dto.HomeworkDto;
import pl.jcommerce.joannajaromin.studentbook.dto.HomeworkDtoWithoutFile;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveHomeworkDto;

import java.io.FileNotFoundException;
import java.util.List;

public interface HomeworkService {

    HomeworkDtoWithoutFile findById(int homeworkId);

    List<HomeworkDtoWithoutFile> findAll();

    void deleteById(int homeworkId);

    HomeworkDtoWithoutFile save(MultipartFile file, SaveHomeworkDto saveHomeworkDto) throws FileNotFoundException;

    HomeworkDto findByIdWithFileContent(int homeworkId);
}
