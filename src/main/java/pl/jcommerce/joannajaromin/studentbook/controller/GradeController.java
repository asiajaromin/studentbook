package pl.jcommerce.joannajaromin.studentbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.jcommerce.joannajaromin.studentbook.dto.GradeDto;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveGradeDto;
import pl.jcommerce.joannajaromin.studentbook.exception.GradeNotFoundException;
import pl.jcommerce.joannajaromin.studentbook.service.GradeService;
import pl.jcommerce.joannajaromin.studentbook.validator.IdConstraint;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class GradeController {

    private final GradeService gradeService;

    @GetMapping("/grades")
    public List<GradeDto> findAll() {
        List<GradeDto> gradeDtoList = gradeService.findAll();
        if (gradeDtoList==null){
            throw new GradeNotFoundException("Brak ocen do wyświetlenia.");
        }
        else return gradeDtoList;
    }

    // I tried to remowe null checking, but then status is OK, but return body is empty
    @GetMapping("/grades/{gradeId}")
    public GradeDto getGrade(@IdConstraint @Valid @PathVariable Integer gradeId){
        GradeDto gradeDto = gradeService.findById(gradeId);
        if (gradeDto==null){
            throw new GradeNotFoundException("Nie znaleziono oceny o id = " + gradeId);
        }
        else
            return gradeDto;
    }

    @PostMapping("/grades")
    public GradeDto saveGrade(@Valid @RequestBody SaveGradeDto grade){
        return gradeService.save(grade);
    }

// When id doesn't exist it creates new item but I don't do anything with it,
// because user shouldn't have access to editing nonexistent grades
    @PutMapping("/grades")
    public GradeDto updateGrade(@Valid @RequestBody GradeDto grade){
        return gradeService.update(grade);
    }

//    when grade doesn't exist exception isn't thrown
//    but status is 404 with message: "Brak wpisu o takim id."
    @DeleteMapping("/grades/{gradeId}")
    public void deleteGrade (@PathVariable int gradeId){
        gradeService.deleteById(gradeId);
    }

}
