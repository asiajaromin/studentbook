package pl.jcommerce.joannajaromin.studentbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jcommerce.joannajaromin.studentbook.dto.HomeworkDto;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaHomeworkConverter;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaSaveHomeworkConverter;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveHomeworkDto;
import pl.jcommerce.joannajaromin.studentbook.entity.Homework;
import pl.jcommerce.joannajaromin.studentbook.repository.HomeworkRepository;

import java.io.File;

@Service
@RequiredArgsConstructor
public class HomeworkServiceImpl implements HomeworkService {

    HomeworkRepository homeworkRepository;
    OrikaSaveHomeworkConverter saveHomeworkConverter;
    OrikaHomeworkConverter homeworkConverter;

    @Override
    public HomeworkDto saveHomework(File file, SaveHomeworkDto saveHomeworkDto) {
        var homework = saveHomeworkConverter.map(saveHomeworkDto,Homework.class);
        byte[] fileData = file.toString().getBytes();
        homework.setFileData(fileData);
        var savedHomework = homeworkRepository.save(homework);
        return homeworkConverter.map(savedHomework,HomeworkDto.class);
    }

}
