package pl.jcommerce.joannajaromin.studentbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.jcommerce.joannajaromin.studentbook.dto.HomeworkDto;
import pl.jcommerce.joannajaromin.studentbook.dto.HomeworkDtoWithoutFile;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveHomeworkDto;
import pl.jcommerce.joannajaromin.studentbook.service.HomeworkService;

@RestController
@RequiredArgsConstructor
public class HomeworkController {

    private final HomeworkService homeworkService;

    @PostMapping(value = "/homeworks", consumes = "multipart/form-data")
    @ResponseBody
    public HomeworkDto uploadHomework(@RequestPart("uploadFile") MultipartFile file,
                                      @RequestPart("saveHomeworkDto") SaveHomeworkDto saveHomeworkDto){
        System.out.println(homeworkService);
        HomeworkDto homeworkDto = homeworkService.saveHomework(file, saveHomeworkDto);
        return homeworkDto;
    }

    @GetMapping("/homeworks/{homeworkId}")
    public HomeworkDtoWithoutFile getHomework (@PathVariable int homeworkId){
        HomeworkDtoWithoutFile homeworkDtoWithoutFile = homeworkService.findById(homeworkId);
        return homeworkDtoWithoutFile;
    }

}
