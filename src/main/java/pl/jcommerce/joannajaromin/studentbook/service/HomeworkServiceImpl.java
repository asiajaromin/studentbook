package pl.jcommerce.joannajaromin.studentbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.jcommerce.joannajaromin.studentbook.dto.HomeworkDtoWithoutFile;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaHomeworkWithoutFileConverter;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaSaveHomeworkConverter;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveHomeworkDto;
import pl.jcommerce.joannajaromin.studentbook.entity.Homework;
import pl.jcommerce.joannajaromin.studentbook.repository.HomeworkRepository;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HomeworkServiceImpl implements HomeworkService {

    private final HomeworkRepository homeworkRepository;
    private final OrikaSaveHomeworkConverter saveHomeworkConverter;
    private final OrikaHomeworkWithoutFileConverter withoutFileConverter;

    @Override
    @Transactional
    public HomeworkDtoWithoutFile save(MultipartFile file, SaveHomeworkDto saveHomeworkDto) {
        var homework = saveHomeworkConverter.map(saveHomeworkDto,Homework.class);
        try {
            byte[] fileData = file.getBytes();
            homework.setFileData(fileData);
        } catch (IOException e) {
            log.info("Unable to read file.", e);
        }
        var savedHomework = homeworkRepository.save(homework);
        return withoutFileConverter.map(savedHomework,HomeworkDtoWithoutFile.class);
    }

    @Override
    @Transactional(readOnly = true)
    public HomeworkDtoWithoutFile findById(int homeworkId) {
        var homework = homeworkRepository.findById(homeworkId);
        return withoutFileConverter.map(homework, HomeworkDtoWithoutFile.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HomeworkDtoWithoutFile> findAll() {
        var homeworks = homeworkRepository.findAll();
        return withoutFileConverter.mapAsList(homeworks,HomeworkDtoWithoutFile.class);
    }

    @Override
    @Transactional(readOnly = true)
    public ByteArrayResource getFileContent(int homeworkId) {
        var homework = homeworkRepository.findById(homeworkId);
        var resource = new ByteArrayResource(homework.getFileData());
        return resource;
    }

    @Override
    @Transactional
    public void deleteById(int homeworkId) {
        homeworkRepository.deleteById(homeworkId);
    }
}
