package pl.jcommerce.joannajaromin.studentbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.jcommerce.joannajaromin.studentbook.dto.HomeworkDto;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveHomeworkDto;
import pl.jcommerce.joannajaromin.studentbook.service.HomeworkService;

@RestController
@RequiredArgsConstructor
public class HomeworkController {

    private HomeworkService homeworkService;

    @PostMapping("/homeworks")
    public HomeworkDto uploadHomework (@RequestParam("file") MultipartFile file,
                                       @RequestParam("groupId") Integer groupId,
                                       @RequestParam("teacherId") Integer teacherId,
                                       @RequestParam("subjectId") Integer subjectId,
                                       @RequestParam("fileName") String fileName,
                                       @RequestParam("fileDescription") String fileDescription){
        var saveHomeworkDto = new SaveHomeworkDto(groupId,teacherId,subjectId,fileName,fileDescription);
        var homeworkDto = homeworkService.saveHomework(file, saveHomeworkDto);
        return homeworkDto;
    }

}
