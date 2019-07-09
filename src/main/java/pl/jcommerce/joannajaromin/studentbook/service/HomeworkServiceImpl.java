package pl.jcommerce.joannajaromin.studentbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.jcommerce.joannajaromin.studentbook.dto.HomeworkDto;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaHomeworkConverter;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaSaveHomeworkConverter;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveHomeworkDto;
import pl.jcommerce.joannajaromin.studentbook.entity.Homework;
import pl.jcommerce.joannajaromin.studentbook.repository.HomeworkRepository;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class HomeworkServiceImpl implements HomeworkService {

    HomeworkRepository homeworkRepository;
    OrikaSaveHomeworkConverter saveHomeworkConverter;
    OrikaHomeworkConverter homeworkConverter;

    @Override
    public HomeworkDto saveHomework(MultipartFile file, SaveHomeworkDto saveHomeworkDto) {
        var homework = saveHomeworkConverter.map(saveHomeworkDto,Homework.class);
        try {
            byte[] fileData = file.getBytes();
            homework.setFileData(fileData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Homework savedHomework = homeworkRepository.save(homework);
        return homeworkConverter.map(savedHomework,HomeworkDto.class);
    }
}
