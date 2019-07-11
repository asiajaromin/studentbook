package pl.jcommerce.joannajaromin.studentbook.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SaveHomeworkDto {

    private Integer groupId;
    private Integer teacherId;
    private Integer subjectId;

    @NotBlank(message = "Podaj nazwę pliku")
    @Size(max=50, message = "Długość nazwy nie może przekraczać 50 znaków.")
    private String fileName;

    @NotBlank(message = "Podaj opis pliku")
    @Size(max=500, message = "Opis pliku nie może przekraczać 500 znaków.")
    private String fileDescription;
}
