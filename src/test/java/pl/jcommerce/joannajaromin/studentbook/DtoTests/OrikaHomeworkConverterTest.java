package pl.jcommerce.joannajaromin.studentbook.DtoTests;

import org.junit.Before;
import org.junit.Test;
import pl.jcommerce.joannajaromin.studentbook.dto.HomeworkDto;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaHomeworkConverter;
import pl.jcommerce.joannajaromin.studentbook.entity.ClassGroup;
import pl.jcommerce.joannajaromin.studentbook.entity.Homework;
import pl.jcommerce.joannajaromin.studentbook.entity.Subject;
import pl.jcommerce.joannajaromin.studentbook.entity.Teacher;

import static org.junit.Assert.assertEquals;
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
        assertEquals(homework.getFileData(),convertedDto.getFileData());
    }

    @Test
    public void canConvertSingleDtoToHomework(){

    }

    @Test
    public void canConvertHomeworksListToDtoList(){

    }

    @Test
    public void canConvertDtoListToHomeworkList(){

    }

}
