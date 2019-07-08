package pl.jcommerce.joannajaromin.studentbook.dto;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;
import pl.jcommerce.joannajaromin.studentbook.entity.Teacher;

@Component
public class OrikaTeacherConverter extends ConfigurableMapper {

    protected void configure(MapperFactory factory){
        factory.classMap(Teacher.class,TeacherDto.class)
                .field("id","id")
                .field("username","username")
                .field("password","password")
                .field("firstName","firstName")
                .field("lastName","lastName")
                .field("email","email")
                .field("groups{id}","groupsIds")
                .byDefault().register();
    }

}
