package pl.jcommerce.joannajaromin.studentbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
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
}
