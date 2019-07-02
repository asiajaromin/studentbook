package pl.jcommerce.joannajaromin.studentbook.dto;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import pl.jcommerce.joannajaromin.studentbook.entity.Grade;
import pl.jcommerce.joannajaromin.studentbook.entity.Subject;

public class OrikaSubjectConverter {
    private final static MapperFacade mapper;

    static {
        final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(Grade.class, GradeDto.class)
                .field("id", "id")
                .field("name", "name")
                .byDefault().register();
        mapper = mapperFactory.getMapperFacade();
    }

    public OrikaSubjectConverter() {
    }

    public SubjectDto mapSubjectToSubjectDto(final Subject subject) {
        SubjectDto subjectDto = mapper.map(subject, SubjectDto.class);
        return subjectDto;
    }
}
