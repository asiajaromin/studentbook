package pl.jcommerce.joannajaromin.studentbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import pl.jcommerce.joannajaromin.studentbook.dto.HomeworkDtoWithoutFile;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveHomeworkDto;
import pl.jcommerce.joannajaromin.studentbook.exception.HomeworkNotFoundException;
import pl.jcommerce.joannajaromin.studentbook.service.HomeworkService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class HomeworkController {

    private final HomeworkService homeworkService;

    @PostMapping(value = "/homeworks", consumes = "multipart/form-data")
    @ResponseBody
    public HomeworkDtoWithoutFile uploadHomework(@RequestPart("uploadFile") MultipartFile file,
                                                 @Valid @RequestPart("saveHomeworkDto") SaveHomeworkDto saveHomeworkDto) {
        // it doesn't work. When file is not attached status is 400, but the message isn't printed
//        if (!file.isEmpty()){
//            HomeworkDtoWithoutFile homeworkDto = homeworkService.saveHomework(file, saveHomeworkDto);
//            return homeworkDto;
//        }
//        else throw new HomeworkFileNotAttached("Brak załączonego pliku");
        return homeworkService.saveHomework(file, saveHomeworkDto);
    }

    @GetMapping("/homeworks/{homeworkId}")
    public HomeworkDtoWithoutFile getHomework(@PathVariable int homeworkId) {
        HomeworkDtoWithoutFile homeworkDtoWithoutFile = homeworkService.findById(homeworkId);
        if (homeworkDtoWithoutFile != null) {
            return homeworkDtoWithoutFile;
        } else {
            throw new HomeworkNotFoundException("Brak zadania domowego o id = " + homeworkId);
        }
//        I tried surrounding it with try/catch, but it doesn't work. When id is not integer there is
//        MethodArgumentTypeMismatchException thrown (status 400) without custom message
//        there seems to be the problem with PathVariable annotations:
//        https://github.com/spring-projects/spring-framework/issues/11041
//        catch (MethodArgumentTypeMismatchException exc){
//            throw new HomeworkNotFoundException("Brak zadania domowego o id = " + homeworkId);
//        }
    }

    @GetMapping("/homeworks")
    public List<HomeworkDtoWithoutFile> getAllHomeworks() {
        List<HomeworkDtoWithoutFile> homeworkDtoWithoutFiles = homeworkService.findAll();
        if (homeworkDtoWithoutFiles == null) {
            throw new HomeworkNotFoundException("Brak zadań do wyświetlenia.");
        } else {
            return homeworkService.findAll();
        }
    }

    @GetMapping("/downloadHomework/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable int fileId) {
        HomeworkDtoWithoutFile homeworkDtoWithoutFile = homeworkService.findById(fileId);
        if (homeworkDtoWithoutFile == null) {
            throw new HomeworkNotFoundException("Nie odnaleziono pliku o id = " + fileId);
        } else {
            ByteArrayResource resource = homeworkService.downloadFile(fileId);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + homeworkDtoWithoutFile.getFileName());
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(resource);
        }
    }

    // used String instead of int to catch exception w przypadku nieprawidłowego formatu id
    @DeleteMapping("/homeworks/{homeworkId}")
    public void deleteHomework(@PathVariable String homeworkId) {
        Integer homeworkIdInt;
        try {
            homeworkIdInt = Integer.parseInt(homeworkId);
            HomeworkDtoWithoutFile homeworkDtoWithoutFile = homeworkService.findById(homeworkIdInt);
            if (homeworkDtoWithoutFile == null) {
                throw new HomeworkNotFoundException("Brak zadania domowego o id = " + homeworkId);
            } else {
                homeworkService.deleteById(homeworkIdInt);
            }
        } catch (HomeworkNotFoundException exc) {
            throw new HomeworkNotFoundException("Brak zadania domowego o id = " + homeworkId);
        } catch (Exception exc){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Id powinno być dodatnią liczbą całkowitą", exc);
        }
    }
}
