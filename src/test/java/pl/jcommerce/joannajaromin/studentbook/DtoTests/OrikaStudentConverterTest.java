package pl.jcommerce.joannajaromin.studentbook.DtoTests;

import org.junit.Before;
import org.junit.Test;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaStudentConverter;
import pl.jcommerce.joannajaromin.studentbook.dto.StudentDto;
import pl.jcommerce.joannajaromin.studentbook.entity.Grade;
import pl.jcommerce.joannajaromin.studentbook.entity.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrikaStudentConverterTest {

    private final int STUDENT_ID = 15;
    private final String FIRST_NAME = "Ireneusz";
    private final String LAST_NAME = "Kwiatkowski";
    private final String EMAIL = "irek.kwiatkowski@twojmail.pl";

    private Student student;
    private StudentDto studentDto;
    private List<Grade> grades = new ArrayList<>();
    private OrikaStudentConverter converter;

    @Before
    public void before(){
        student = new Student(STUDENT_ID,FIRST_NAME,LAST_NAME,EMAIL,grades);
        studentDto = new StudentDto(STUDENT_ID,FIRST_NAME,LAST_NAME,EMAIL);
        converter = new OrikaStudentConverter();
    }

    @Test
    public void canConvertSingleStudentToDto(){
        StudentDto convertedDtoStudent = converter.map(student,StudentDto.class);
        assertEquals(student.getId(),convertedDtoStudent.getId());
        assertEquals(student.getFirstName(),convertedDtoStudent.getFirstName());
        assertEquals(student.getLastName(),convertedDtoStudent.getLastName());
        assertEquals(student.getEmail(),convertedDtoStudent.getEmail());
    }

    @Test
    public void canConvertSingeDtoToStudent(){
        Student convertedStudent = converter.map(studentDto,Student.class);
        assertEquals(studentDto.getId(),convertedStudent.getId());
        assertEquals(studentDto.getFirstName(),convertedStudent.getFirstName());
        assertEquals(studentDto.getLastName(),convertedStudent.getLastName());
        assertEquals(studentDto.getEmail(),convertedStudent.getEmail());
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
    }
}
