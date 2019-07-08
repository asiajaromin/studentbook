package pl.jcommerce.joannajaromin.studentbook.dto;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;
import pl.jcommerce.joannajaromin.studentbook.entity.ClassGroup;

@Component
public class OrikaClassGroupConverter extends ConfigurableMapper {

    protected void configure(MapperFactory factory){
        factory.classMap(ClassGroup.class,ClassGroupDto.class)
                .field("id","id")
                .field("name","name")
                .field("teachers{id}","teachersIds")
                .byDefault().register();
    }
}
