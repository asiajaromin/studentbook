package pl.jcommerce.joannajaromin.studentbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.jcommerce.joannajaromin.studentbook.dto.GradeDto;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveGradeDto;
import pl.jcommerce.joannajaromin.studentbook.exception.GradeNotFoundException;
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
        List<GradeDto> gradeDtoList = gradeService.findAll();
        if (gradeDtoList==null){
            throw new GradeNotFoundException("Brak ocen do wyświetlenia.");
        }
        else return gradeDtoList;
    }

    @GetMapping("/grades/{gradeId}")
    public GradeDto getGrade(@PathVariable int gradeId){
        log.info("Szukam oceny o id {}", gradeId);
        GradeDto gradeDto = gradeService.findById(gradeId);
        if (gradeDto==null){
            throw new GradeNotFoundException("Nie znaleziono oceny o id = " + gradeId);
        }
        else return gradeDto;
    }

    @PostMapping("/grades")
    public GradeDto saveGrade(@Valid @RequestBody SaveGradeDto grade){
        return gradeService.save(grade);
    }

    @PutMapping("/grades")
    public GradeDto updateGrade(@Valid @RequestBody GradeDto grade){
        GradeDto originalGrade = gradeService.findById(grade.getId());
        if (originalGrade==null){
            throw new GradeNotFoundException("Nie znaleziono oceny o id = " + grade.getId());
        }
        // to be deleted - only left for you to see this solution
        // This exception is unnecessary - teacher will not have access to nonexisting students/subjects
        // incorrect grade automatically throws MethodArgumentNotValidException with status 400 due to validation
//        else {
//            try {
//                GradeDto gradeDto = gradeService.update(grade);
//                return gradeDto;
//            } catch (JpaObjectRetrievalFailureException exc) {
//                throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,"Nieprawidłowe wartości pól", exc);
//            }
//        }
        else return gradeService.update(grade);
    }

//    @DeleteMapping("/grades/{gradeId}")
//    public void deleteGrade (@PathVariable int gradeId){
//        GradeDto originalGrade = gradeService.findById(gradeId);
//        if (originalGrade==null){
//            throw new GradeNotFoundException("Nie znaleziono oceny o id = " + gradeId);
//        }
//        else gradeService.deleteById(gradeId);
//    }

    @DeleteMapping("/grades/{gradeId}")
    public void deleteGrade (@PathVariable int gradeId){
        try {
            if (gradeService.findById(gradeId) != null){
                gradeService.deleteById(gradeId);
            }
            else throw new GradeNotFoundException();
        }
        catch (GradeNotFoundException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Nie znaleziono oceny o id = " + gradeId, exc);
        }
    }
}
