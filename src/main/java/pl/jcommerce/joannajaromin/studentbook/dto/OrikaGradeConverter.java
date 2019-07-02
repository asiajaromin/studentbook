package pl.jcommerce.joannajaromin.studentbook.dto;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import pl.jcommerce.joannajaromin.studentbook.entity.Grade;

public class OrikaGradeConverter {

    private final static MapperFacade mapper;

    static {
        final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(Grade.class, GradeDto.class)
                .field("id", "id")
                .field("student.id", "studentId")
                .field("subject.id", "subjectId")
                .field("grade", "grade")
                .byDefault().register();
        mapper = mapperFactory.getMapperFacade();
    }

    public OrikaGradeConverter() {
    }

    public GradeDto mapGradeToGradeDto(final Grade grade) {
        GradeDto gradeDto = mapper.map(grade, GradeDto.class);
        return gradeDto;
    }
}