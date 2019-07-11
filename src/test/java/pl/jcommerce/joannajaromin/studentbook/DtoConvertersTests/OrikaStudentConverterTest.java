package pl.jcommerce.joannajaromin.studentbook.DtoConvertersTests;

import org.junit.Before;
import org.junit.Test;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaStudentConverter;
import pl.jcommerce.joannajaromin.studentbook.dto.StudentDto;
import pl.jcommerce.joannajaromin.studentbook.entity.ClassGroup;
import pl.jcommerce.joannajaromin.studentbook.entity.Grade;
import pl.jcommerce.joannajaromin.studentbook.entity.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrikaStudentConverterTest {

    private final int STUDENT_ID1 = 15;
    private final int STUDENT_ID2 = 6;
    private final String FIRST_NAME1 = "Ireneusz";
    private final String FIRST_NAME2 = "Arkadiusz";
    private final String LAST_NAME = "Kwiatkowski";
    private final String EMAIL = "irek.kwiatkowski@twojmail.pl";
    private final int CLASS_GROUP_ID = 2;

    private List<Grade> grades = new ArrayList<>();
    private OrikaStudentConverter converter;
    private ClassGroup classGroup;

    @Before
    public void before(){
        classGroup = new ClassGroup();
        classGroup.setId(CLASS_GROUP_ID);
        converter = new OrikaStudentConverter();
    }

    @Test
    public void canConvertSingleStudentToDto(){
        var student = new Student(STUDENT_ID1,FIRST_NAME1,LAST_NAME,EMAIL,grades,classGroup);
        var convertedDtoStudent = converter.map(student,StudentDto.class);
        assertEquals(student.getId(),convertedDtoStudent.getId());
        assertEquals(student.getFirstName(),convertedDtoStudent.getFirstName());
        assertEquals(student.getLastName(),convertedDtoStudent.getLastName());
        assertEquals(student.getEmail(),convertedDtoStudent.getEmail());
        assertEquals(student.getClassGroup().getId(),convertedDtoStudent.getClassGroupId());
    }

    @Test
    public void canConvertSingeDtoToStudent(){
        var studentDto = new StudentDto(STUDENT_ID2,FIRST_NAME2,LAST_NAME,EMAIL,CLASS_GROUP_ID);
        var convertedStudent = converter.map(studentDto,Student.class);
        assertEquals(studentDto.getId(),convertedStudent.getId());
        assertEquals(studentDto.getFirstName(),convertedStudent.getFirstName());
        assertEquals(studentDto.getLastName(),convertedStudent.getLastName());
        assertEquals(studentDto.getEmail(),convertedStudent.getEmail());
        assertEquals(studentDto.getClassGroupId(),convertedStudent.getClassGroup().getId());
    }

    @Test
    public void canConvertStudentsListToDtoList(){
        var student1 = new Student(STUDENT_ID1,FIRST_NAME2,LAST_NAME,EMAIL,grades,classGroup);
        var student2 = new Student(STUDENT_ID2,FIRST_NAME1,LAST_NAME,EMAIL,grades,classGroup);
        var student3 = new Student(STUDENT_ID1,FIRST_NAME2,LAST_NAME,EMAIL,grades,classGroup);
        var students = Arrays.asList(student1,student2,student3);
        var convertedDtoList = converter.mapAsList(students,StudentDto.class);
        assertEquals(students.size(),convertedDtoList.size());
        for(var i = 0; i < students.size(); i++) {
            var student = students.get(i);
            var studentDto = convertedDtoList.get(i);
            assertEquals(student.getId(), studentDto.getId());
            assertEquals(student.getFirstName(), studentDto.getFirstName());
            assertEquals(student.getLastName(), studentDto.getLastName());
            assertEquals(student.getEmail(), studentDto.getEmail());
            assertEquals(student.getClassGroup().getId(), studentDto.getClassGroupId());
        }
    }

    @Test
    public void canConvertDtoListToStudentsList(){
        var studentDto1 = new StudentDto(STUDENT_ID2,FIRST_NAME1,LAST_NAME,EMAIL,CLASS_GROUP_ID);
        var studentDto2 = new StudentDto(STUDENT_ID1,FIRST_NAME2,LAST_NAME,EMAIL,CLASS_GROUP_ID);
        var dtoStudents = Arrays.asList(studentDto1,studentDto2);
        List<Student> convertedStudentList = converter.mapAsList(dtoStudents,Student.class);
        assertEquals(dtoStudents.size(),convertedStudentList.size());
        for (var i = 0; i < dtoStudents.size(); i++) {
            StudentDto studentDto = dtoStudents.get(i);
            Student student = convertedStudentList.get(i);
            assertEquals(studentDto.getId(), student.getId());
            assertEquals(studentDto.getFirstName(), student.getFirstName());
            assertEquals(studentDto.getLastName(), student.getLastName());
            assertEquals(studentDto.getEmail(), student.getEmail());
            assertEquals(studentDto.getClassGroupId(), student.getClassGroup().getId());
        }
    }
}
