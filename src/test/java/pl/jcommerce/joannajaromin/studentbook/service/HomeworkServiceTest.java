package pl.jcommerce.joannajaromin.studentbook.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import pl.jcommerce.joannajaromin.studentbook.dto.HomeworkDto;
import pl.jcommerce.joannajaromin.studentbook.dto.HomeworkDtoWithoutFile;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaHomeworkConverter;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaHomeworkWithoutFileConverter;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaSaveHomeworkConverter;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveHomeworkDto;
import pl.jcommerce.joannajaromin.studentbook.entity.ClassGroup;
import pl.jcommerce.joannajaromin.studentbook.entity.Homework;
import pl.jcommerce.joannajaromin.studentbook.entity.Subject;
import pl.jcommerce.joannajaromin.studentbook.entity.Teacher;
import pl.jcommerce.joannajaromin.studentbook.repository.HomeworkRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HomeworkServiceTest {

    private final int HOMEWORK_ID1 = 4;
    private final int HOMEWORK_ID2 = 31;
    private final int GROUP_ID = 78;
    private final int TEACHER_ID = 14;
    private final int SUBJECT_ID = 9;
    private final String FILE_NAME = "Zadanie domowe";
    private final String FILE_DESCRIPTION = "Zadanie domowe dla klasy B13 na 25.07.2019";
    private final String DATA_STRING1 = "Napisz sprawozdanie z wakacji na 38 stron A4";
    private final String DATA_STRING2 = "Oblicz średnią prędkość swojego samochodu z poprzedniego miesiąca.";
    private final byte[] FILE_DATA1 = DATA_STRING1.getBytes();
    private final byte[] FILE_DATA2 = DATA_STRING2.getBytes();
    private final MultipartFile multipartFile = new MockMultipartFile(FILE_NAME, FILE_DATA1);

    private HomeworkService homeworkService;
    private HomeworkRepository homeworkRepository;
    private OrikaSaveHomeworkConverter saveHomeworkConverter;
    private OrikaHomeworkConverter homeworkConverter;
    private OrikaHomeworkWithoutFileConverter homeworkWithoutFileConverter;
    private Homework homework;
    private HomeworkDto homeworkDto;
    private SaveHomeworkDto saveHomeworkDto;
    private HomeworkDtoWithoutFile homeworkDtoInfo;
    private ClassGroup classGroup;
    private Teacher teacher;
    private Subject subject;

    @Before
    public void before() {
        classGroup = new ClassGroup();
        classGroup.setId(GROUP_ID);
        teacher = new Teacher();
        teacher.setId(TEACHER_ID);
        subject = new Subject();
        subject.setId(SUBJECT_ID);
        homework = new Homework(HOMEWORK_ID1, classGroup, teacher, subject, FILE_NAME,
                FILE_DESCRIPTION, FILE_DATA1);
        homeworkDto = new HomeworkDto(HOMEWORK_ID1, GROUP_ID, TEACHER_ID, SUBJECT_ID, FILE_NAME,
                FILE_DESCRIPTION, FILE_DATA1);
        homeworkDtoInfo = new HomeworkDtoWithoutFile(HOMEWORK_ID1, GROUP_ID, TEACHER_ID, SUBJECT_ID,
                FILE_NAME, FILE_DESCRIPTION);
        saveHomeworkDto = new SaveHomeworkDto(GROUP_ID, TEACHER_ID, SUBJECT_ID, FILE_NAME, FILE_DESCRIPTION);
        homeworkRepository = mock(HomeworkRepository.class);
        saveHomeworkConverter = mock(OrikaSaveHomeworkConverter.class);
        homeworkConverter = mock(OrikaHomeworkConverter.class);
        homeworkWithoutFileConverter = mock(OrikaHomeworkWithoutFileConverter.class);
        homeworkService = new HomeworkServiceImpl(homeworkRepository, saveHomeworkConverter,
                homeworkConverter, homeworkWithoutFileConverter);
    }

    @Test
    public void canSaveHomework() {
        var homeworkWithoutFile = new Homework(HOMEWORK_ID1,classGroup,teacher,subject,FILE_NAME,
                FILE_DESCRIPTION,null);
        when(saveHomeworkConverter.map(saveHomeworkDto,Homework.class)).thenReturn(homeworkWithoutFile);
        when(homeworkRepository.save(homework)).thenReturn(homework);
        when(homeworkWithoutFileConverter.map(homework, HomeworkDtoWithoutFile.class)).thenReturn(homeworkDtoInfo);
        var savedHomeworkDto = homeworkService.saveHomework(multipartFile, saveHomeworkDto);
        assertEquals(homeworkDtoInfo, savedHomeworkDto);
    }

    @Test
    public void canGetSingleHomeworkInfo() {
        when(homeworkRepository.findById(HOMEWORK_ID1)).thenReturn(homework);
        when(homeworkWithoutFileConverter.map(homework,HomeworkDtoWithoutFile.class)).thenReturn(homeworkDtoInfo);
        var foundHomeworkDtoWithoutFile = homeworkService.findById(HOMEWORK_ID1);
        assertEquals(homeworkDtoInfo,foundHomeworkDtoWithoutFile);
    }

    @Test
    public void canGetAllHomeworksInfo() {
        Homework homework1 = new Homework(HOMEWORK_ID1,classGroup,teacher,subject,FILE_NAME,FILE_DESCRIPTION,FILE_DATA1);
        Homework homework2 = new Homework(HOMEWORK_ID2,classGroup,teacher,subject,FILE_NAME,FILE_DESCRIPTION,FILE_DATA2);
        HomeworkDtoWithoutFile homeworkInfo1 = new HomeworkDtoWithoutFile(HOMEWORK_ID1,GROUP_ID,TEACHER_ID,SUBJECT_ID,FILE_NAME,FILE_DESCRIPTION);
        HomeworkDtoWithoutFile homeworkInfo2 = new HomeworkDtoWithoutFile(HOMEWORK_ID2,GROUP_ID,TEACHER_ID,SUBJECT_ID,FILE_NAME,FILE_DESCRIPTION);
        List<Homework> homeworksList = Arrays.asList(homework1,homework2);
        List<HomeworkDtoWithoutFile> homeworksInfo = Arrays.asList(homeworkInfo1,homeworkInfo2);
        when(homeworkRepository.findAll()).thenReturn(homeworksList);
        when(homeworkWithoutFileConverter.mapAsList(homeworksList,HomeworkDtoWithoutFile.class)).thenReturn(homeworksInfo);
        List<HomeworkDtoWithoutFile> foundHomeworksList = homeworkService.findAll();
        assertEquals(homeworksInfo,foundHomeworksList);
    }

    @Test
    public void canGetHomeworkFile() {
        when(homeworkRepository.findById(HOMEWORK_ID1)).thenReturn(homework);
        ByteArrayResource resource = new ByteArrayResource(FILE_DATA1);
        ByteArrayResource invalidResource = new ByteArrayResource(FILE_DATA2);
        ByteArrayResource downloadedResource = homeworkService.downloadFile(HOMEWORK_ID1);
        assertEquals(resource,downloadedResource);
        assertNotEquals(invalidResource,downloadedResource);
    }

    @Test
    public void canDeleteHomework() {
        homeworkService.deleteById(HOMEWORK_ID2);
        verify(homeworkRepository).deleteById(HOMEWORK_ID2);
    }
}
