package pl.jcommerce.joannajaromin.studentbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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
        return gradeService.findAll();
    }

    @GetMapping("/grades/{gradeId}")
    public GradeDto getGrade(@PathVariable int gradeId){
            return gradeService.findById(gradeId);
    }

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping("/grades")
    public GradeDto saveGrade(@Valid @RequestBody SaveGradeDto grade){
        return gradeService.save(grade);
    }

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PutMapping("/grades")
    public GradeDto updateGrade(@Valid @RequestBody GradeDto grade){
        return gradeService.update(grade);
    }

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @DeleteMapping("/grades/{gradeId}")
    public void deleteGrade (@PathVariable int gradeId){
        gradeService.deleteById(gradeId);
    }

}
