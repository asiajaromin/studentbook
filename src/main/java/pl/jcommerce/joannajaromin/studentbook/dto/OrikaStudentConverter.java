package pl.jcommerce.joannajaromin.studentbook.dto;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;
import pl.jcommerce.joannajaromin.studentbook.entity.Student;

@Component
public class OrikaStudentConverter extends ConfigurableMapper {

    protected void configure(MapperFactory factory){
        factory.classMap(Student.class, StudentDto.class)
                .field("id", "id")
                .field("firstName", "firstName")
                .field("lastName", "lastName")
                .field("email", "email")
                .field("classGroup.id","classGroupId")
                .byDefault().register();
    }
}