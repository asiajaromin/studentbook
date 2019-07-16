package pl.jcommerce.joannajaromin.studentbook.DtoConvertersTests;

import org.junit.Before;
import org.junit.Test;
import pl.jcommerce.joannajaromin.studentbook.dto.HomeworkDto;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaHomeworkConverter;
import pl.jcommerce.joannajaromin.studentbook.entity.ClassGroup;
import pl.jcommerce.joannajaromin.studentbook.entity.Homework;
import pl.jcommerce.joannajaromin.studentbook.entity.Subject;
import pl.jcommerce.joannajaromin.studentbook.entity.Teacher;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class OrikaHomeworkConverterTest {

    private final int HOMEWORK_ID1 = 15;
    private final int HOMEWORK_ID2 = 3;
    private final int GROUP_ID = 345;
    private final int TEACHER_ID = 4;
    private final int SUBJECT_ID = 8;
    private final String FILE_NAME = "Zadanie1";
    private final String FILE_DESCRIPTION = "Referat do przygotowania na następne zajęcia.";
    private final String DATA_STRING1 = "Napisz referat o historii Polski. Referat powinien mieć 15 stron.";
    private final String DATA_STRING2 = "Narysuj ładne drzewo.";
    private final byte[] FILE_DATA1 = DATA_STRING1.getBytes();
    private final byte[] FILE_DATA2 = DATA_STRING2.getBytes();

    private ClassGroup group;
    private Teacher teacher;
    private Subject subject;
    private OrikaHomeworkConverter converter;

    @Before
    public void before(){
        group = new ClassGroup();
        group.setId(GROUP_ID);
        teacher = new Teacher();
        teacher.setId(TEACHER_ID);
        subject = new Subject();
        subject.setId(SUBJECT_ID);
        converter = new OrikaHomeworkConverter();
    }

    @Test
    public void canConvertSingleHomeworkToDto(){
        var homework = new Homework(HOMEWORK_ID1,group,teacher,subject,FILE_NAME,FILE_DESCRIPTION,FILE_DATA2);
        var convertedDto = converter.map(homework,HomeworkDto.class);
        assertEquals(homework.getId(),convertedDto.getId());
        assertEquals(homework.getGroup().getId(),convertedDto.getGroupId());
        assertEquals(homework.getTeacher().getId(),convertedDto.getTeacherId());
        assertEquals(homework.getSubject().getId(),convertedDto.getSubjectId());
        assertEquals(homework.getFileName(),convertedDto.getFileName());
        assertEquals(homework.getFileDescription(),convertedDto.getFileDescription());
        assertArrayEquals(homework.getFileData(),convertedDto.getFileData());
        var homeworkText = new String(homework.getFileData());
        var dtoText = new String(convertedDto.getFileData());
        assertEquals(homeworkText,dtoText);
    }

    @Test
    public void canConvertSingleDtoToHomework(){
        var homeworkDto = new HomeworkDto(HOMEWORK_ID1,GROUP_ID,TEACHER_ID,SUBJECT_ID,FILE_NAME,FILE_DESCRIPTION,
                FILE_DATA1);
        var convertedHomework = converter.map(homeworkDto,Homework.class);
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
        var homework1 = new Homework(HOMEWORK_ID1,group,teacher,subject,FILE_NAME,FILE_DESCRIPTION,FILE_DATA2);
        var homework2 = new Homework(HOMEWORK_ID2,group,teacher,subject,FILE_NAME,FILE_DESCRIPTION,FILE_DATA1);
        var homeworksList = Arrays.asList(homework1,homework2);
        var convertedDtoList = converter.mapAsList(homeworksList,HomeworkDto.class);
        assertEquals(homeworksList.size(),convertedDtoList.size());
        for (var i = 0; i < homeworksList.size(); i++) {
            var homework = homeworksList.get(i);
            var homeworkDto = convertedDtoList.get(i);
            assertEquals(homework.getId(), homeworkDto.getId());
            assertEquals(homework.getGroup().getId(), homeworkDto.getGroupId());
            assertEquals(homework.getTeacher().getId(), homeworkDto.getTeacherId());
            assertEquals(homework.getSubject().getId(), homeworkDto.getSubjectId());
            assertEquals(homework.getFileName(), homeworkDto.getFileName());
            assertEquals(homework.getFileDescription(), homeworkDto.getFileDescription());
            assertArrayEquals(homework.getFileData(), homeworkDto.getFileData());
        }
    }

    @Test
    public void canConvertDtoListToHomeworkList(){
        var homeworkDto1 = new HomeworkDto(HOMEWORK_ID1,GROUP_ID,TEACHER_ID,SUBJECT_ID,FILE_NAME,
                FILE_DESCRIPTION,FILE_DATA1);
        var homeworkDto2 = new HomeworkDto(HOMEWORK_ID2,GROUP_ID,TEACHER_ID,SUBJECT_ID,FILE_NAME,
                FILE_DESCRIPTION,FILE_DATA2);
        var homeworkDto3 = new HomeworkDto(HOMEWORK_ID2,GROUP_ID,TEACHER_ID,SUBJECT_ID,FILE_NAME,
                FILE_DESCRIPTION,FILE_DATA1);
        var dtoList = Arrays.asList(homeworkDto1,homeworkDto2,homeworkDto3);
        var convertedHomeworkList = converter.mapAsList(dtoList,Homework.class);
        assertEquals(dtoList.size(),convertedHomeworkList.size());
        for (var i = 0; i < dtoList.size(); i++) {
            var homeworkDto = dtoList.get(i);
            var homework = convertedHomeworkList.get(i);
            assertEquals(homeworkDto.getId(), homework.getId());
            assertEquals(homeworkDto.getGroupId(), homework.getGroup().getId());
            assertEquals(homeworkDto.getTeacherId(), homework.getTeacher().getId());
            assertEquals(homeworkDto.getSubjectId(), homework.getSubject().getId());
            assertEquals(homeworkDto.getFileName(), homework.getFileName());
            assertEquals(homeworkDto.getFileDescription(), homework.getFileDescription());
            assertArrayEquals(homeworkDto.getFileData(), homework.getFileData());
        }
    }

}
