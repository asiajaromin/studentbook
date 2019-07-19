package pl.jcommerce.joannajaromin.studentbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeworkController {

    private final HomeworkService homeworkService;

    @PostMapping(value = "/homeworks", consumes = "multipart/form-data")
    @ResponseBody
    public HomeworkDtoWithoutFile uploadHomework(@RequestPart("uploadFile") MultipartFile file,
                                                 @Valid @RequestPart("saveHomeworkDto")
                                                         SaveHomeworkDto saveHomeworkDto)
            throws FileNotFoundException {
        return homeworkService.save(file, saveHomeworkDto);
    }

    @GetMapping("/homeworks/{homeworkId}")
    public HomeworkDtoWithoutFile getHomework(@PathVariable int homeworkId) {
        return homeworkService.findById(homeworkId);
    }

    @GetMapping("/homeworks")
    public List<HomeworkDtoWithoutFile> getAllHomeworks() {
        return homeworkService.findAll();
    }

    @GetMapping("/homeworks/fileContent/{homeworkId}")
    public ResponseEntity<ByteArrayResource> getHomeworkFileContent(@PathVariable int homeworkId) {
        HomeworkDto homeworkDto = homeworkService.findByIdWithFileContent(homeworkId);
        HttpHeaders headers = prepareHeaders(homeworkDto);
        ByteArrayResource resource = new ByteArrayResource(homeworkDto.getFileData());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    private HttpHeaders prepareHeaders(HomeworkDto homeworkDto) {
        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" +
                homeworkDto.getFileName());
        return headers;
    }

    @DeleteMapping("/homeworks/{homeworkId}")
    @PreAuthorize("hasAuthority('USER')")
    public void deleteHomework(@PathVariable int homeworkId) {
        homeworkService.deleteById(homeworkId);
    }
}
