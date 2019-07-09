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

    private final int GROUP_ID1 = 92;
    private final int GROUP_ID2 = 12;
    private final String GROUP_NAME = "5G";
    private final List<Teacher> TEACHERS = new ArrayList<>();
    private final List<Student> STUDENTS = new ArrayList<>();
    private final List<Homework> HOMEWORKS = new ArrayList<>();

    private OrikaClassGroupConverter converter;

    @Before
    public void before(){
        converter = new OrikaClassGroupConverter();
    }

    @Test
    public void canConvertSingleGroupToDto(){
        var group = new ClassGroup(GROUP_ID1,GROUP_NAME,STUDENTS,HOMEWORKS,TEACHERS);
        var convertedDtoGroup = converter.map(group,ClassGroupDto.class);
        assertEquals(group.getId(),convertedDtoGroup.getId());
        assertEquals(group.getName(),convertedDtoGroup.getName());
    }

    @Test
    public void canConvertSingleDtoToGroup(){
        var groupDto = new ClassGroupDto(GROUP_ID2,GROUP_NAME);
        var convertedGroup = converter.map(groupDto,ClassGroup.class);
        assertEquals(groupDto.getId(),convertedGroup.getId());
        assertEquals(groupDto.getName(),convertedGroup.getName());
    }

    @Test
    public void canConvertGroupsListToDtoList(){
        var group1 = new ClassGroup(GROUP_ID1,GROUP_NAME,STUDENTS,HOMEWORKS,TEACHERS);
        var group2 = new ClassGroup(GROUP_ID2,GROUP_NAME,STUDENTS,HOMEWORKS,TEACHERS);
        var groupsList = Arrays.asList(group1,group2);
        var convertedDtoList = converter.mapAsList(groupsList,ClassGroupDto.class);
        assertEquals(groupsList.size(),convertedDtoList.size());
        for (var i = 0; i < groupsList.size(); i++) {
            var group = groupsList.get(i);
            var groupDto = convertedDtoList.get(i);
            assertEquals(group.getId(), groupDto.getId());
            assertEquals(group.getName(), groupDto.getName());
        }
    }

    @Test
    public void canConvertDtoListToGroupsList(){
        var groupDto1 = new ClassGroupDto(GROUP_ID1,GROUP_NAME);
        var groupDto2 = new ClassGroupDto(GROUP_ID2,GROUP_NAME);
        var dtoList = Arrays.asList(groupDto1,groupDto2);
        var convertedGroupList = converter.mapAsList(dtoList,ClassGroup.class);
        for (var i = 0; i < dtoList.size(); i++) {
            var groupDto = dtoList.get(i);
            var group = convertedGroupList.get(i);
            assertEquals(groupDto.getId(), group.getId());
            assertEquals(groupDto.getName(), group.getName());
        }
    }
}
