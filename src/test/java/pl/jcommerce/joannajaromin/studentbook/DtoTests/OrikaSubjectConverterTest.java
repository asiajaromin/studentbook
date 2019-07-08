package pl.jcommerce.joannajaromin.studentbook.DtoTests;

import org.junit.Before;
import org.junit.Test;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaSubjectConverter;
import pl.jcommerce.joannajaromin.studentbook.dto.SubjectDto;
import pl.jcommerce.joannajaromin.studentbook.entity.Grade;
import pl.jcommerce.joannajaromin.studentbook.entity.Homework;
import pl.jcommerce.joannajaromin.studentbook.entity.Subject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrikaSubjectConverterTest {

    private final int SUBJECT_ID = 12;
    private final String NAME = "Hiszpański";
    private final List<Grade> GRADES = new ArrayList<>();
    private final List<Homework> HOMEWORKS = new ArrayList<>();

    private Subject subject;
    private SubjectDto subjectDto;
    private OrikaSubjectConverter converter;

    @Before
    public void before(){
        subject = new Subject(SUBJECT_ID,NAME,GRADES,HOMEWORKS);
        subjectDto = new SubjectDto(SUBJECT_ID,NAME);
        converter = new OrikaSubjectConverter();
    }

    @Test
    public void canConvertSingleSubjectToDto(){
        SubjectDto convertedDto = converter.map(subject,SubjectDto.class);
        assertEquals(subject.getId(),convertedDto.getId());
        assertEquals(subject.getName(),convertedDto.getName());
    }

    @Test
    public void canConvertSingleDtoToSubject(){
        Subject convertedSubject = converter.map(subjectDto,Subject.class);
        assertEquals(subjectDto.getId(),convertedSubject.getId());
        assertEquals(subjectDto.getName(),convertedSubject.getName());
    }

    @Test
    public void canConvertSubjectListToDto(){
        List<Subject> subjectList = Arrays.asList(subject,subject,subject);
        List<SubjectDto> convertedDtoList = converter.mapAsList(subjectList,SubjectDto.class);
        Subject secondSubject = subjectList.get(1);
        SubjectDto secondDtoSubject = convertedDtoList.get(1);
        assertEquals(secondSubject.getId(),secondDtoSubject.getId());
        assertEquals(secondSubject.getName(),secondDtoSubject.getName());
    }

    @Test
    public void canConvertDtoListToSubject(){
        List<SubjectDto> dtoList = Arrays.asList(subjectDto,subjectDto,subjectDto);
        List<Subject> convertedSubjectList = converter.mapAsList(dtoList,Subject.class);
        SubjectDto firstDtoSubject = dtoList.get(0);
        Subject firstSubject = convertedSubjectList.get(0);
        assertEquals(firstDtoSubject.getId(),firstSubject.getId());
        assertEquals(firstDtoSubject.getName(),firstSubject.getName());
    }
}
