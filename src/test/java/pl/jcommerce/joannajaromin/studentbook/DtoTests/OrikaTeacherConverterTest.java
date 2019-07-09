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
    private final String PASSWORD1 = "kowalski123";
    private final String PASSWORD2 = "janek11212";
    private final String FIRST_NAME = "Jan";
    private final String LAST_NAME1 = "Kowalski";
    private final String LAST_NAME2 = "Nowak";
    private final String EMAIL = "jan.kowalski@gmail.com";
    private final List<Homework> HOMEWORKS = new ArrayList<>();
    private final List<ClassGroup> GROUPS = new ArrayList<>();

    private OrikaTeacherConverter converter;

    @Before
    public void before(){
        converter = new OrikaTeacherConverter();
    }

    @Test
    public void canConvertSingleTeacherToDto(){
        var teacher = new Teacher(TEACHER_ID,USERNAME,PASSWORD1,FIRST_NAME,LAST_NAME1,EMAIL,HOMEWORKS,GROUPS);
        var convertedDtoTeacher = converter.map(teacher,TeacherDto.class);
        assertEquals(teacher.getId(),convertedDtoTeacher.getId());
        assertEquals(teacher.getUsername(),convertedDtoTeacher.getUsername());
        assertEquals(teacher.getPassword(),convertedDtoTeacher.getPassword());
        assertEquals(teacher.getFirstName(),convertedDtoTeacher.getFirstName());
        assertEquals(teacher.getLastName(),convertedDtoTeacher.getLastName());
        assertEquals(teacher.getEmail(),convertedDtoTeacher.getEmail());
    }

    @Test
    public void canConvertSingleDtoToTeacher(){
        var teacherDto = new TeacherDto(TEACHER_ID,USERNAME,PASSWORD2,FIRST_NAME,LAST_NAME2,EMAIL);
        var convertedTeacher = converter.map(teacherDto,Teacher.class);
        assertEquals(teacherDto.getId(),convertedTeacher.getId());
        assertEquals(teacherDto.getUsername(),convertedTeacher.getUsername());
        assertEquals(teacherDto.getPassword(),convertedTeacher.getPassword());
        assertEquals(teacherDto.getFirstName(),convertedTeacher.getFirstName());
        assertEquals(teacherDto.getLastName(),convertedTeacher.getLastName());
        assertEquals(teacherDto.getEmail(),convertedTeacher.getEmail());
    }

    @Test
    public void canConvertTeachersListToDtoList(){
        var teacher1 = new Teacher(TEACHER_ID,USERNAME,PASSWORD1,FIRST_NAME,LAST_NAME2,EMAIL,HOMEWORKS,GROUPS);
        var teacher2 = new Teacher(TEACHER_ID,USERNAME,PASSWORD2,FIRST_NAME,LAST_NAME1,EMAIL,HOMEWORKS,GROUPS);
        var teacher3 = new Teacher(TEACHER_ID,USERNAME,PASSWORD1,FIRST_NAME,LAST_NAME1,EMAIL,HOMEWORKS,GROUPS);
        var teachersList = Arrays.asList(teacher1,teacher2,teacher3);
        var convertedDtoList = converter.mapAsList(teachersList,TeacherDto.class);
        assertEquals(teachersList.size(),convertedDtoList.size());
        for(var i = 0; i < teachersList.size(); i++) {
            Teacher teacher = teachersList.get(i);
            TeacherDto teacherDto = convertedDtoList.get(i);
            assertEquals(teacher.getId(), teacherDto.getId());
            assertEquals(teacher.getUsername(), teacherDto.getUsername());
            assertEquals(teacher.getPassword(), teacherDto.getPassword());
            assertEquals(teacher.getFirstName(), teacherDto.getFirstName());
            assertEquals(teacher.getLastName(), teacherDto.getLastName());
            assertEquals(teacher.getEmail(), teacherDto.getEmail());
        }
    }

    @Test
    public void canConvertDtoListToTeachersList(){
        var teacherDto1 = new TeacherDto(TEACHER_ID,USERNAME,PASSWORD1,FIRST_NAME,LAST_NAME1,EMAIL);
        var teacherDto2 = new TeacherDto(TEACHER_ID,USERNAME,PASSWORD2,FIRST_NAME,LAST_NAME2,EMAIL);
        var teacherDto3 = new TeacherDto(TEACHER_ID,USERNAME,PASSWORD1,FIRST_NAME,LAST_NAME2,EMAIL);
        var teacherDto4 = new TeacherDto(TEACHER_ID,USERNAME,PASSWORD2,FIRST_NAME,LAST_NAME1,EMAIL);
        var dtoList = Arrays.asList(teacherDto1,teacherDto2,teacherDto3,teacherDto4);
        var convertedTeachersList = converter.mapAsList(dtoList,Teacher.class);
        assertEquals(dtoList.size(),convertedTeachersList.size());
        for (var i = 0; i < dtoList.size(); i++) {
            var teacherDto = dtoList.get(1);
            var teacher = convertedTeachersList.get(1);
            assertEquals(teacherDto.getId(), teacher.getId());
            assertEquals(teacherDto.getUsername(), teacher.getUsername());
            assertEquals(teacherDto.getPassword(), teacher.getPassword());
            assertEquals(teacherDto.getFirstName(), teacher.getFirstName());
            assertEquals(teacherDto.getLastName(), teacher.getLastName());
            assertEquals(teacherDto.getEmail(), teacher.getEmail());
        }
    }
}
