package pl.jcommerce.joannajaromin.studentbook.DtoTests;

import org.junit.Before;
import org.junit.Test;
import pl.jcommerce.joannajaromin.studentbook.dto.ClassGroupDto;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaClassGroupConverter;
import pl.jcommerce.joannajaromin.studentbook.entity.ClassGroup;
import pl.jcommerce.joannajaromin.studentbook.entity.Homework;
import pl.jcommerce.joannajaromin.studentbook.entity.Student;
import pl.jcommerce.joannajaromin.studentbook.entity.Teacher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrikaClassGroupConverterTest {

    private final int GROUP_ID = 92;
    private final String GROUP_NAME = "5G";
    private final List<Teacher> TEACHERS = new ArrayList<>();
    private final List<Student> STUDENTS = new ArrayList<>();
    private final List<Homework> HOMEWORKS = new ArrayList<>();

    private ClassGroup group;
    private ClassGroupDto groupDto;
    private OrikaClassGroupConverter converter;

    @Before
    public void before(){
        group = new ClassGroup(GROUP_ID,GROUP_NAME,STUDENTS,HOMEWORKS,TEACHERS);
        groupDto = new ClassGroupDto(GROUP_ID,GROUP_NAME);
        converter = new OrikaClassGroupConverter();
    }

    @Test
    public void canConvertSingleGroupToDto(){
        ClassGroupDto convertedDtoGroup = converter.map(group,ClassGroupDto.class);
        assertEquals(group.getId(),convertedDtoGroup.getId());
        assertEquals(group.getName(),convertedDtoGroup.getName());
    }

    @Test
    public void canConvertSingleDtoToGroup(){
        ClassGroup convertedGroup = converter.map(groupDto,ClassGroup.class);
        assertEquals(groupDto.getId(),convertedGroup.getId());
        assertEquals(groupDto.getName(),convertedGroup.getName());
    }

    @Test
    public void canConvertGroupsListToDtoList(){
        List<ClassGroup> groupsList = Arrays.asList(group,group,group);
        List<ClassGroupDto> convertedDtoList = converter.mapAsList(groupsList,ClassGroupDto.class);
        ClassGroup thirdGroup = groupsList.get(2);
        ClassGroupDto thirdDto = convertedDtoList.get(2);
        assertEquals(thirdGroup.getId(),thirdDto.getId());
        assertEquals(thirdGroup.getName(),thirdDto.getName());
    }

    @Test
    public void canConvertDtoListToGroupsList(){
        List<ClassGroupDto> dtoList = Arrays.asList(groupDto);
        List<ClassGroup> convertedGroupList = converter.mapAsList(dtoList,ClassGroup.class);
        ClassGroupDto firstDto = dtoList.get(0);
        ClassGroup firstGroup = convertedGroupList.get(0);
        assertEquals(firstDto.getId(),firstGroup.getId());
        assertEquals(firstDto.getName(),firstGroup.getName());
    }
}
