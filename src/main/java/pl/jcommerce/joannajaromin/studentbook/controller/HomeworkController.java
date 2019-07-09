package pl.jcommerce.joannajaromin.studentbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.jcommerce.joannajaromin.studentbook.dto.HomeworkDto;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveHomeworkDto;
import pl.jcommerce.joannajaromin.studentbook.service.HomeworkService;

@RestController
@RequiredArgsConstructor
public class HomeworkController {

    private HomeworkService homeworkService;

//    public static final String uploadingDir = System.getProperty("user.dir") + "/uploadingDir/";
//
//    @PostMapping("/homeworks")
//    public HomeworkDto homeworkDto (@RequestParam("uploadingFiles") MultipartFile uploadedFile,
//                                    @RequestParam("groupId") Integer groupId,
//                                    @RequestParam("teacherId") Integer teacherId,
//                                    @RequestParam("subjectId") Integer subjectId,
//                                    @RequestParam("fileName") String fileName,
//                                    @RequestParam("fileDescription") String fileDescription)
//            throws IOException {
//        File file = new File(uploadingDir + uploadedFile.getOriginalFilename());
//            uploadedFile.transferTo(file);
//        var saveHomeworkDto = new SaveHomeworkDto(groupId,teacherId,subjectId,fileName,fileDescription);
//        HomeworkDto homeworkDto = homeworkService.saveHomework(file, saveHomeworkDto);
//        return homeworkDto;
//    }

    @PostMapping(value = "/homeworks", consumes = "multipart/form-data")
    @ResponseBody
    public HomeworkDto uploadHomework(@RequestPart("uploadFile") MultipartFile file,
                                      @RequestPart("saveHomeworkDto") SaveHomeworkDto saveHomeworkDto){
        HomeworkDto homeworkDto = homeworkService.saveHomework(file, saveHomeworkDto);
        return homeworkDto;
    }

}
