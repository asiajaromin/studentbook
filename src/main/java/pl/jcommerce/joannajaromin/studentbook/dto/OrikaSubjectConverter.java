package pl.jcommerce.joannajaromin.studentbook.dto;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import pl.jcommerce.joannajaromin.studentbook.entity.Subject;

public class OrikaSubjectConverter extends ConfigurableMapper {

    protected void configure(MapperFactory factory){
        factory.classMap(Subject.class, SubjectDto.class)
                .field("id", "id")
                .field("name", "name")
                .byDefault().register();
    }
}