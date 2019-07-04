package pl.jcommerce.joannajaromin.studentbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.jcommerce.joannajaromin.studentbook.dto.GradeDto;
import pl.jcommerce.joannajaromin.studentbook.service.GradeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

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
        return gradeService.save(grade);
    }

    @PutMapping("/grades")
    public GradeDto updateGrade(@RequestBody GradeDto grade){
        return gradeService.save(grade);
    }

    @DeleteMapping("/grades/{gradeId}")
    public void deleteGrade (@PathVariable int gradeId){
        gradeService.deleteById(gradeId);
    }

}
