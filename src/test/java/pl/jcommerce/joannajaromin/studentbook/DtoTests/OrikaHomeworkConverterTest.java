package pl.jcommerce.joannajaromin.studentbook.DtoTests;

import org.junit.Before;
import org.junit.Test;
import pl.jcommerce.joannajaromin.studentbook.dto.HomeworkDto;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaHomeworkConverter;
import pl.jcommerce.joannajaromin.studentbook.entity.ClassGroup;
import pl.jcommerce.joannajaromin.studentbook.entity.Homework;
import pl.jcommerce.joannajaromin.studentbook.entity.Subject;
import pl.jcommerce.joannajaromin.studentbook.entity.Teacher;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrikaHomeworkConverterTest {

    private final int HOMEWORK_ID = 15;
    private final int GROUP_ID = 345;
    private final int TEACHER_ID = 4;
    private final int SUBJECT_ID = 8;
    private final String FILE_NAME = "Zadanie1";
    private final String FILE_DESCRIPTION = "Referat do przygotowania na następne zajęcia.";
    private final String DATA_STRING = "Napisz referat o historii Polski. Referat powinien mieć 15 stron.";
    private final byte[] FILE_DATA = DATA_STRING.getBytes();


    private ClassGroup group;
    private Teacher teacher;
    private Subject subject;
    private Homework homework;
    private HomeworkDto homeworkDto;
    private OrikaHomeworkConverter converter;

    @Before
    public void before(){
        group = mock(ClassGroup.class);
        when(group.getId()).thenReturn(GROUP_ID);
        teacher = mock(Teacher.class);
        when(teacher.getId()).thenReturn(TEACHER_ID);
        subject = mock(Subject.class);
        when(subject.getId()).thenReturn(SUBJECT_ID);
        homework = new Homework(HOMEWORK_ID,group,teacher,subject,FILE_NAME,FILE_DESCRIPTION,FILE_DATA);
        homeworkDto = new HomeworkDto(HOMEWORK_ID,GROUP_ID,TEACHER_ID,SUBJECT_ID,FILE_NAME,FILE_DESCRIPTION,FILE_DATA);
        converter = new OrikaHomeworkConverter();
    }

    @Test
    public void canConvertSingleHomeworkToDto(){
        HomeworkDto convertedDto = converter.map(homework,HomeworkDto.class);
        assertEquals(homework.getId(),convertedDto.getId());
        assertEquals(homework.getGroup().getId(),convertedDto.getGroupId());
        assertEquals(homework.getTeacher().getId(),convertedDto.getTeacherId());
        assertEquals(homework.getSubject().getId(),convertedDto.getSubjectId());
        assertEquals(homework.getFileName(),convertedDto.getFileName());
        assertEquals(homework.getFileDescription(),convertedDto.getFileDescription());
        assertArrayEquals(homework.getFileData(),convertedDto.getFileData());
        String homeworkText = new String(homework.getFileData());
        String dtoText = new String(convertedDto.getFileData());
        assertEquals(homeworkText,dtoText);
    }

    @Test
    public void canConvertSingleDtoToHomework(){
        Homework convertedHomework = converter.map(homeworkDto,Homework.class);
        assertEquals(homeworkDto.getId(),convertedHomework.getId());
        assertEquals(homeworkDto.getGroupId(),convertedHomework.getGroup().getId());
        assertEquals(homeworkDto.getTeacherId(),convertedHomework.getTeacher().getId());
        assertEquals(homeworkDto.getSubjectId(),convertedHomework.getSubject().getId());
        assertEquals(homeworkDto.getFileName(),convertedHomework.getFileName());
        assertEquals(homeworkDto.getFileDescription(),convertedHomework.getFileDescription());
        assertArrayEquals(homeworkDto.getFileData(),convertedHomework.getFileData());
    }

    @Test
    public void canConvertHomeworksListToDtoList(){
        List<Homework> homeworksList = Arrays.asList(homework,homework,homework);
        List<HomeworkDto> convertedDtoList = converter.mapAsList(homeworksList,HomeworkDto.class);
        Homework secondHomework = homeworksList.get(1);
        HomeworkDto secondDto = convertedDtoList.get(1);
        assertEquals(secondHomework.getId(),secondDto.getId());
        assertEquals(secondHomework.getGroup().getId(),secondDto.getGroupId());
        assertEquals(secondHomework.getTeacher().getId(),secondDto.getTeacherId());
        assertEquals(secondHomework.getSubject().getId(),secondDto.getSubjectId());
        assertEquals(secondHomework.getFileName(),secondDto.getFileName());
        assertEquals(secondHomework.getFileDescription(),secondDto.getFileDescription());
        assertArrayEquals(secondHomework.getFileData(),secondDto.getFileData());
    }

    @Test
    public void canConvertDtoListToHomeworkList(){
        List<HomeworkDto> dtoList = Arrays.asList(homeworkDto,homeworkDto,homeworkDto,homeworkDto,homeworkDto);
        List<Homework> convertedHomeworkList = converter.mapAsList(dtoList,Homework.class);
        HomeworkDto fifthDto = dtoList.get(4);
        Homework fifthHomework = convertedHomeworkList.get(4);
        assertEquals(fifthDto.getId(),fifthHomework.getId());
        assertEquals(fifthDto.getGroupId(),fifthHomework.getGroup().getId());
        assertEquals(fifthDto.getTeacherId(),fifthHomework.getTeacher().getId());
        assertEquals(fifthDto.getSubjectId(),fifthHomework.getSubject().getId());
        assertEquals(fifthDto.getFileName(),fifthHomework.getFileName());
        assertEquals(fifthDto.getFileDescription(),fifthHomework.getFileDescription());
        assertArrayEquals(fifthDto.getFileData(),fifthHomework.getFileData());
    }

}
