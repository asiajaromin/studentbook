package pl.jcommerce.joannajaromin.studentbook.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SaveGradeDto {

    private Integer studentId;
    private Integer subjectId;

    @NotNull(message = "Podaj ocenę.")
    @Range(min=1,max=6,message = "Ocena powinna być liczbą całkowitą z przedziału od 1 do 6.")
    private Integer grade;
}
