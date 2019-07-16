package pl.jcommerce.joannajaromin.studentbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.jcommerce.joannajaromin.studentbook.dto.HomeworkDtoWithoutFile;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveHomeworkDto;
import pl.jcommerce.joannajaromin.studentbook.service.HomeworkService;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeworkController {

    private final HomeworkService homeworkService;

    @PostMapping(value = "/homeworks", consumes = "multipart/form-data")
    @ResponseBody
    public HomeworkDtoWithoutFile uploadHomework(@RequestPart("uploadFile") MultipartFile file,
                                      @RequestPart("saveHomeworkDto") SaveHomeworkDto saveHomeworkDto)
            throws FileNotFoundException {
        var homeworkDto = homeworkService.save(file, saveHomeworkDto);
        return homeworkDto;
    }

    @GetMapping("/homeworks/{homeworkId}")
    public HomeworkDtoWithoutFile getHomework (@PathVariable int homeworkId){
        var homeworkDtoWithoutFile = homeworkService.findById(homeworkId);
        return homeworkDtoWithoutFile;
    }

    @GetMapping("/homeworks")
    public List<HomeworkDtoWithoutFile> getAllHomeworks(){
        return homeworkService.findAll();
    }

    @GetMapping("/homeworks/fileContent/{homeworkId}")
    public ResponseEntity<ByteArrayResource> getHomeworkFileContent (@PathVariable int homeworkId){
        var resource = homeworkService.getFileContent(homeworkId);
        var homeworkDtoWithoutFile = homeworkService.findById(homeworkId);
        HttpHeaders headers = prepareHeaders(homeworkDtoWithoutFile);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    private HttpHeaders prepareHeaders(HomeworkDtoWithoutFile homeworkDtoWithoutFile) {
        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+
                homeworkDtoWithoutFile.getFileName());
        return headers;
    }

    @DeleteMapping("/homeworks/{homeworkId}")
    public void deleteHomework (@PathVariable int homeworkId){
        homeworkService.deleteById(homeworkId);
    }

}
