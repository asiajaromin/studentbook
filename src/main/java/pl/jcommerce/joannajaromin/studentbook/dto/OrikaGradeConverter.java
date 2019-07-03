package pl.jcommerce.joannajaromin.studentbook.dto;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;
import pl.jcommerce.joannajaromin.studentbook.entity.Grade;

@Component
public class OrikaGradeConverter extends ConfigurableMapper {

    protected void configure(MapperFactory factory){
        factory.classMap(Grade.class,GradeDto.class)
                .field("id", "id")
                .field("student.id", "studentId")
                .field("subject.id", "subjectId")
                .field("grade", "grade")
                .byDefault().register();
    }
}