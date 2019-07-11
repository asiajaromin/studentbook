package pl.jcommerce.joannajaromin.studentbook.converter;

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

    private final int SUBJECT_ID1 = 12;
    private final int SUBJECT_ID2 = 78;
    private final String NAME1 = "Hiszpański";
    private final String NAME2 = "Chiński";
    private final List<Grade> GRADES = new ArrayList<>();
    private final List<Homework> HOMEWORKS = new ArrayList<>();

    private OrikaSubjectConverter converter;

    @Before
    public void before(){
        converter = new OrikaSubjectConverter();
    }

    @Test
    public void canConvertSingleSubjectToDto(){
        var subject = new Subject(SUBJECT_ID1,NAME2,GRADES,HOMEWORKS);
        var convertedDto = converter.map(subject,SubjectDto.class);
        assertEquals(subject.getId(),convertedDto.getId());
        assertEquals(subject.getName(),convertedDto.getName());
    }

    @Test
    public void canConvertSingleDtoToSubject(){
        var subjectDto = new SubjectDto(SUBJECT_ID2,NAME1);
        var convertedSubject = converter.map(subjectDto,Subject.class);
        assertEquals(subjectDto.getId(),convertedSubject.getId());
        assertEquals(subjectDto.getName(),convertedSubject.getName());
    }

    @Test
    public void canConvertSubjectListToDto(){
        var subject1 = new Subject(SUBJECT_ID1,NAME2,GRADES,HOMEWORKS);
        var subject2 = new Subject(SUBJECT_ID2,NAME1,GRADES,HOMEWORKS);
        var subject3 = new Subject(SUBJECT_ID1,NAME1,GRADES,HOMEWORKS);
        var subjectList = Arrays.asList(subject2,subject3,subject1);
        var convertedDtoList = converter.mapAsList(subjectList,SubjectDto.class);
        assertEquals(subjectList.size(),convertedDtoList.size());
        for(var i = 0; i < subjectList.size(); i++) {
            var subject = subjectList.get(i);
            var subjectDto = convertedDtoList.get(i);
            assertEquals(subject.getId(), subjectDto.getId());
            assertEquals(subject.getName(), subjectDto.getName());
        }
    }

    @Test
    public void canConvertDtoListToSubject(){
        var subjectDto1 = new SubjectDto(SUBJECT_ID1,NAME1);
        var subjectDto2 = new SubjectDto(SUBJECT_ID2,NAME2);
        var dtoList = Arrays.asList(subjectDto1,subjectDto2);
        var convertedSubjectList = converter.mapAsList(dtoList,Subject.class);
        assertEquals(dtoList.size(),convertedSubjectList.size());
        for (var i = 0; i < dtoList.size(); i++) {
            SubjectDto subjectDto = dtoList.get(i);
            Subject subject = convertedSubjectList.get(i);
            assertEquals(subjectDto.getId(), subject.getId());
            assertEquals(subjectDto.getName(), subject.getName());
        }
    }
}
