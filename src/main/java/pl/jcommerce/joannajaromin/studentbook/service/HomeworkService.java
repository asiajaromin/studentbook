package pl.jcommerce.joannajaromin.studentbook.service;

import pl.jcommerce.joannajaromin.studentbook.dto.HomeworkDto;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveHomeworkDto;

import java.io.File;

public interface HomeworkService {

    HomeworkDto saveHomework(File file, SaveHomeworkDto saveHomeworkDto);
}
