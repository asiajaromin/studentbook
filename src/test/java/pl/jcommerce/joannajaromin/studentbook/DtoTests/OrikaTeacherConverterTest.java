package pl.jcommerce.joannajaromin.studentbook.DtoTests;

import org.junit.Before;
import org.junit.Test;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaTeacherConverter;
import pl.jcommerce.joannajaromin.studentbook.dto.TeacherDto;
import pl.jcommerce.joannajaromin.studentbook.entity.ClassGroup;
import pl.jcommerce.joannajaromin.studentbook.entity.Homework;
import pl.jcommerce.joannajaromin.studentbook.entity.Teacher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrikaTeacherConverterTest {

    private final int TEACHER_ID = 2;
    private final String USERNAME = "kowalski";
    private final String PASSWORD = "kowalski123";
    private final String FIRST_NAME = "Jan";
    private final String LAST_NAME = "Kowalski";
    private final String EMAIL = "jan.kowalski@gmail.com";
    private final List<Homework> HOMEWORKS = new ArrayList<>();
    private final List<ClassGroup> GROUPS = new ArrayList<>();

    private OrikaTeacherConverter converter;
    private Teacher teacher;
    private TeacherDto teacherDto;


    @Before
    public void before(){
        converter = new OrikaTeacherConverter();
        teacher = new Teacher(TEACHER_ID,USERNAME,PASSWORD,FIRST_NAME,LAST_NAME,EMAIL,HOMEWORKS,GROUPS);
        teacherDto = new TeacherDto(TEACHER_ID,USERNAME,PASSWORD,FIRST_NAME,LAST_NAME,EMAIL);
    }

    @Test
    public void canConvertSingleTeacherToDto(){
        TeacherDto convertedDtoTeacher = converter.map(teacher,TeacherDto.class);
        assertEquals(teacher.getId(),convertedDtoTeacher.getId());
        assertEquals(teacher.getUsername(),convertedDtoTeacher.getUsername());
        assertEquals(teacher.getPassword(),convertedDtoTeacher.getPassword());
        assertEquals(teacher.getFirstName(),convertedDtoTeacher.getFirstName());
        assertEquals(teacher.getLastName(),convertedDtoTeacher.getLastName());
        assertEquals(teacher.getEmail(),convertedDtoTeacher.getEmail());
    }

    @Test
    public void canConvertSingleDtoToTeacher(){
        Teacher convertedTeacher = converter.map(teacherDto,Teacher.class);
        assertEquals(teacherDto.getId(),convertedTeacher.getId());
        assertEquals(teacherDto.getUsername(),convertedTeacher.getUsername());
        assertEquals(teacherDto.getPassword(),convertedTeacher.getPassword());
        assertEquals(teacherDto.getFirstName(),convertedTeacher.getFirstName());
        assertEquals(teacherDto.getLastName(),convertedTeacher.getLastName());
        assertEquals(teacherDto.getEmail(),convertedTeacher.getEmail());
    }

    @Test
    public void canConvertTeachersListToDtoList(){
        List<Teacher> teachersList = Arrays.asList(teacher,teacher);
        List<TeacherDto> convertedDtoList = converter.mapAsList(teachersList,TeacherDto.class);
        Teacher firstTeacher = teachersList.get(0);
        TeacherDto firstDtoTeacher = convertedDtoList.get(0);
        assertEquals(firstTeacher.getId(),firstDtoTeacher.getId());
        assertEquals(firstTeacher.getUsername(),firstDtoTeacher.getUsername());
        assertEquals(firstTeacher.getPassword(),firstDtoTeacher.getPassword());
        assertEquals(firstTeacher.getFirstName(),firstDtoTeacher.getFirstName());
        assertEquals(firstTeacher.getLastName(),firstDtoTeacher.getLastName());
        assertEquals(firstTeacher.getEmail(),firstDtoTeacher.getEmail());
    }

    @Test
    public void canConvertDtoListToTeachersList(){
        List<TeacherDto> dtoList = Arrays.asList(teacherDto,teacherDto,teacherDto);
        List<Teacher> convertedTeachersList = converter.mapAsList(dtoList,Teacher.class);
        TeacherDto secondDtoTeacher = dtoList.get(1);
        Teacher secondTeacher = convertedTeachersList.get(1);
        assertEquals(secondDtoTeacher.getId(),secondTeacher.getId());
        assertEquals(secondDtoTeacher.getUsername(),secondTeacher.getUsername());
        assertEquals(secondDtoTeacher.getPassword(),secondTeacher.getPassword());
        assertEquals(secondDtoTeacher.getFirstName(),secondTeacher.getFirstName());
        assertEquals(secondDtoTeacher.getLastName(),secondTeacher.getLastName());
        assertEquals(secondDtoTeacher.getEmail(),secondTeacher.getEmail());
    }

}
