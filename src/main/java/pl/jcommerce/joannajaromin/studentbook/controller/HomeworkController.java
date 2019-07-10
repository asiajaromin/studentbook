package pl.jcommerce.joannajaromin.studentbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
        HomeworkDto homeworkDto = homeworkService.saveHomework(file, saveHomeworkDto);
        return homeworkDto;
    }

    @GetMapping("/homeworks/{homeworkId}")
    public HomeworkDtoWithoutFile getHomework (@PathVariable int homeworkId){
        HomeworkDtoWithoutFile homeworkDtoWithoutFile = homeworkService.findById(homeworkId);
        return homeworkDtoWithoutFile;
    }

    // do usunięcia - chwilowo zostawiłam, bo teoretycznie przez multipart też powinno działać
    @GetMapping(value = "/homeworkFile/{fileId}", consumes = "multipart/form-data")
    public MultipartFile getHomeworkFile (@PathVariable int fileId){
        MultipartFile homeworkFile = homeworkService.getFile(fileId);
        return homeworkFile;
    }

    @GetMapping("/downloadHomework/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile (@PathVariable int fileId){
        ByteArrayResource resource = homeworkService.downloadFile(fileId);
        HomeworkDtoWithoutFile homeworkDtoWithoutFile = homeworkService.findById(fileId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+homeworkDtoWithoutFile.getFileName());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);

    }

}
