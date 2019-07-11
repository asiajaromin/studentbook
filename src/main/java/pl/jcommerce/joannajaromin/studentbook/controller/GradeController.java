package pl.jcommerce.joannajaromin.studentbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.jcommerce.joannajaromin.studentbook.dto.GradeDto;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveGradeDto;
import pl.jcommerce.joannajaromin.studentbook.service.GradeService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GradeController {

    private final GradeService gradeService;

    @GetMapping("/grades")
    public List<GradeDto> findAll() {
        log.info("Szukam ocen");
        return gradeService.findAll();
    }

    @GetMapping("/grades/{gradeId}")
    public GradeDto getGrade(@PathVariable int gradeId){
        log.info("Szukam oceny o id {}", gradeId);
        return gradeService.findById(gradeId);
    }

    @PostMapping("/grades")
    public GradeDto saveGrade(@Valid @RequestBody SaveGradeDto grade){
        return gradeService.save(grade);
    }

    @PutMapping("/grades")
    public GradeDto updateGrade(@Valid @RequestBody GradeDto grade){
        return gradeService.update(grade);
    }

    @DeleteMapping("/grades/{gradeId}")
    public void deleteGrade (@PathVariable int gradeId){
        gradeService.deleteById(gradeId);
    }

}
