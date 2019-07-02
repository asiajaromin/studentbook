package pl.jcommerce.joannajaromin.studentbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.jcommerce.joannajaromin.studentbook.dto.GradeDto;
import pl.jcommerce.joannajaromin.studentbook.service.GradeService;

import java.util.List;

@RestController
public class GradeController {

    private GradeService gradeService;

    @Autowired
    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping("/grades")
    public List<GradeDto> findAll(){
        return gradeService.findAll();
    }

    @GetMapping("/grades/{gradeId}")
    public GradeDto getGrade(@PathVariable int gradeId){
        GradeDto grade = gradeService.findById(gradeId);
        if(grade == null){
            throw new RuntimeException("Brak oceny z id = " + gradeId);
        }
        return grade;
    }

    @PostMapping("/grades")
    public GradeDto saveGrade(@RequestBody GradeDto grade){
        grade.setId(0);
        gradeService.save(grade);
        return grade;
    }

    @PutMapping("/grades")
    public GradeDto updateGrade(@RequestBody GradeDto grade){
        gradeService.save(grade);
        return grade;
    }

    @DeleteMapping("/grades/{gradeId}")
    public String deleteGrade (@PathVariable int gradeId){
        GradeDto gradeDto = gradeService.findById(gradeId);
        if (gradeDto == null){
            throw new RuntimeException("Brak oceny o id: " + gradeId);
        }
        gradeService.deleteById(gradeId);
        return "Ocena usunięta";
    }

}
