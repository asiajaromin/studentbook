package pl.jcommerce.joannajaromin.studentbook.DtoTests;

import org.junit.Before;
import org.junit.Test;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaStudentConverter;
import pl.jcommerce.joannajaromin.studentbook.dto.StudentDto;
import pl.jcommerce.joannajaromin.studentbook.entity.ClassGroup;
import pl.jcommerce.joannajaromin.studentbook.entity.Grade;
import pl.jcommerce.joannajaromin.studentbook.entity.Student;
import pl.jcommerce.joannajaromin.studentbook.entity.Teacher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrikaStudentConverterTest {

    private final int STUDENT_ID = 15;
    private final String FIRST_NAME = "Ireneusz";
    private final String LAST_NAME = "Kwiatkowski";
    private final String EMAIL = "irek.kwiatkowski@twojmail.pl";
    private final int CLASS_GROUP_ID = 2;
    private final String CLASS_GROUP_NAME = "2C";

    private Student student;
    private Teacher teacher;
    private StudentDto studentDto;
    private List<Grade> grades = new ArrayList<>();
    private OrikaStudentConverter converter;
    private ClassGroup classGroup;

    @Before
    public void before(){
        classGroup = mock(ClassGroup.class);
        student = new Student(STUDENT_ID,FIRST_NAME,LAST_NAME,EMAIL,grades,classGroup);
        studentDto = new StudentDto(STUDENT_ID,FIRST_NAME,LAST_NAME,EMAIL,CLASS_GROUP_ID);
        converter = new OrikaStudentConverter();
        when(classGroup.getId()).thenReturn(CLASS_GROUP_ID);
    }

    @Test
    public void canConvertSingleStudentToDto(){
        StudentDto convertedDtoStudent = converter.map(student,StudentDto.class);
        assertEquals(student.getId(),convertedDtoStudent.getId());
        assertEquals(student.getFirstName(),convertedDtoStudent.getFirstName());
        assertEquals(student.getLastName(),convertedDtoStudent.getLastName());
        assertEquals(student.getEmail(),convertedDtoStudent.getEmail());
        assertEquals(student.getClassGroup().getId(),convertedDtoStudent.getClassGroupId());
    }

    @Test
    public void canConvertSingeDtoToStudent(){
        Student convertedStudent = converter.map(studentDto,Student.class);
        assertEquals(studentDto.getId(),convertedStudent.getId());
        assertEquals(studentDto.getFirstName(),convertedStudent.getFirstName());
        assertEquals(studentDto.getLastName(),convertedStudent.getLastName());
        assertEquals(studentDto.getEmail(),convertedStudent.getEmail());
        assertEquals(studentDto.getClassGroupId(),convertedStudent.getClassGroup().getId());
    }

    @Test
    public void canConvertStudentsListToDtoList(){
        List<Student> students = Arrays.asList(student,student,student,student);
        List<StudentDto> convertedDtoList = converter.mapAsList(students,StudentDto.class);
        Student firstStudent = students.get(0);
        StudentDto firstDtoStudent = convertedDtoList.get(0);
        assertEquals(firstStudent.getId(),firstDtoStudent.getId());
        assertEquals(firstStudent.getFirstName(),firstDtoStudent.getFirstName());
        assertEquals(firstStudent.getLastName(),firstDtoStudent.getLastName());
        assertEquals(firstStudent.getEmail(),firstDtoStudent.getEmail());
        assertEquals(firstStudent.getClassGroup().getId(),firstDtoStudent.getClassGroupId());
    }

    @Test
    public void canConvertDtoListToStudentsList(){
        List<StudentDto> dtoStudents = Arrays.asList(studentDto,studentDto,studentDto,studentDto,studentDto);
        List<Student> convertedStudentList = converter.mapAsList(dtoStudents,Student.class);
        StudentDto fourthDtoStudent = dtoStudents.get(3);
        Student fourthStudent = convertedStudentList.get(3);
        assertEquals(fourthDtoStudent.getId(),fourthStudent.getId());
        assertEquals(fourthDtoStudent.getFirstName(),fourthStudent.getFirstName());
        assertEquals(fourthDtoStudent.getLastName(),fourthStudent.getLastName());
        assertEquals(fourthDtoStudent.getEmail(),fourthStudent.getEmail());
        assertEquals(fourthDtoStudent.getClassGroupId(),fourthStudent.getClassGroup().getId());
    }
}
