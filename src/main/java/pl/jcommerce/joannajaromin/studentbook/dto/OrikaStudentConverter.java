package pl.jcommerce.joannajaromin.studentbook.dto;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import pl.jcommerce.joannajaromin.studentbook.entity.Grade;
import pl.jcommerce.joannajaromin.studentbook.entity.Student;

public class OrikaStudentConverter {

    private final static MapperFacade mapper;

    static {
        final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(Student.class, StudentDto.class)
                .field("id", "id")
                .field("firstName", "firstName")
                .field("lastName", "lastName")
                .field("email", "email")
                .byDefault().register();
        mapper = mapperFactory.getMapperFacade();
    }

    public OrikaStudentConverter() {
    }

    public StudentDto mapStudentToStudentDto(final Student student) {
        StudentDto studentDto = mapper.map(student, StudentDto.class);
        return studentDto;
    }
}
