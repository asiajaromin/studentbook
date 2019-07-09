package pl.jcommerce.joannajaromin.studentbook.dto;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;
import pl.jcommerce.joannajaromin.studentbook.entity.Homework;

@Component
public class OrikaSaveHomeworkConverter extends ConfigurableMapper {

    protected void configure(MapperFactory factory){
        factory.classMap(Homework.class,HomeworkDto.class)
                .field("group.id","groupId")
                .field("teacher.id","teacherId")
                .field("subject.id","subjectId")
                .field("fileName","fileName")
                .field("fileDescription","fileDescription")
                .byDefault().register();
    }
}
