package pl.jcommerce.joannajaromin.studentbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.jcommerce.joannajaromin.studentbook.dto.GradeDto;
import pl.jcommerce.joannajaromin.studentbook.service.GradeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GradeController {

    private GradeService gradeService;

    @GetMapping("/grades")
    public List<GradeDto> findAll(){
        return gradeService.findAll();
    }

    @GetMapping("/grades/{gradeId}")
    public GradeDto getGrade(@PathVariable int gradeId){
        return gradeService.findById(gradeId);
    }

    @PostMapping("/grades")
    public GradeDto saveGrade(@RequestBody GradeDto grade){
        gradeService.save(grade);
        return grade;
    }

    @PutMapping("/grades")
    public GradeDto updateGrade(@RequestBody GradeDto grade){
        gradeService.save(grade);
        return grade;
    }

    @DeleteMapping("/grades/{gradeId}")
    public void deleteGrade (@PathVariable int gradeId){
        gradeService.deleteById(gradeId);
    }

}
