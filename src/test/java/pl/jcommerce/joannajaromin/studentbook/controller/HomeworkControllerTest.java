package pl.jcommerce.joannajaromin.studentbook.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.jcommerce.joannajaromin.studentbook.dto.HomeworkDto;
import pl.jcommerce.joannajaromin.studentbook.dto.HomeworkDtoWithoutFile;
import pl.jcommerce.joannajaromin.studentbook.dto.SaveHomeworkDto;
import pl.jcommerce.joannajaromin.studentbook.service.HomeworkService;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertArrayEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(HomeworkController.class)
public class HomeworkControllerTest {

    private final int HOMEWORK_ID1 = 4;
    private final int HOMEWORK_ID2 = 16;
    private final int GROUP_ID = 74;
    private final int TEACHER_ID = 9;
    private final int SUBJECT_ID = 51;
    private final String FILE_NAME1 = "Zadania";
    private final String FILE_NAME2 = "Wypracowanie";
    private final String FILE_DESCRIPTION1 = "Zadania do rozwiązania";
    private final String FILE_DESCRIPTION2 = "Wypracowanie do napisania";
    private final String FILE_CONTENT = "Napisz wypracowanie na dowolny temat i o dowolnej długości.";
    private final byte[] FILE_CONTENT_BYTES = FILE_CONTENT.getBytes();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private HomeworkService homeworkService;

    @Test
    @WithMockUser
    public void canGetHomeworkWithoutFile() throws Exception {
        var dto = new HomeworkDtoWithoutFile(HOMEWORK_ID1, GROUP_ID, TEACHER_ID, SUBJECT_ID,
                FILE_NAME1, FILE_DESCRIPTION1);
        given(homeworkService.findById(HOMEWORK_ID1)).willReturn(dto);
        mvc.perform(get("/homeworks/" + HOMEWORK_ID1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(HOMEWORK_ID1))
                .andExpect(jsonPath("$.groupId").value(GROUP_ID))
                .andExpect(jsonPath("$.teacherId").value(TEACHER_ID))
                .andExpect(jsonPath("$.subjectId").value(SUBJECT_ID))
                .andExpect(jsonPath("$.fileName").value(FILE_NAME1))
                .andExpect(jsonPath("$.fileDescription").value(FILE_DESCRIPTION1));
    }

    @Test
    @WithMockUser
    public void canGetAllHomeworksWithoutFile() throws Exception {
        var dto1 = new HomeworkDtoWithoutFile(HOMEWORK_ID1, GROUP_ID, TEACHER_ID, SUBJECT_ID,
                FILE_NAME1, FILE_DESCRIPTION1);
        var dto2 = new HomeworkDtoWithoutFile(HOMEWORK_ID2, GROUP_ID, TEACHER_ID, SUBJECT_ID,
                FILE_NAME2, FILE_DESCRIPTION2);
        var list = Arrays.asList(dto1,dto2);
        given(homeworkService.findAll()).willReturn(list);
        mvc.perform(get("/homeworks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(HOMEWORK_ID1))
                .andExpect(jsonPath("$.[0].groupId").value(GROUP_ID))
                .andExpect(jsonPath("$.[0].teacherId").value(TEACHER_ID))
                .andExpect(jsonPath("$.[0].subjectId").value(SUBJECT_ID))
                .andExpect(jsonPath("$.[0].fileName").value(FILE_NAME1))
                .andExpect(jsonPath("$.[0].fileDescription").value(FILE_DESCRIPTION1))
                .andExpect(jsonPath("$.[1].id").value(HOMEWORK_ID2))
                .andExpect(jsonPath("$.[1].groupId").value(GROUP_ID))
                .andExpect(jsonPath("$.[1].teacherId").value(TEACHER_ID))
                .andExpect(jsonPath("$.[1].subjectId").value(SUBJECT_ID))
                .andExpect(jsonPath("$.[1].fileName").value(FILE_NAME2))
                .andExpect(jsonPath("$.[1].fileDescription").value(FILE_DESCRIPTION2));
    }

    @Test
    @WithMockUser
    public void canGetFileContent() throws Exception {
        var dto = new HomeworkDto(HOMEWORK_ID2, GROUP_ID, TEACHER_ID, SUBJECT_ID, FILE_NAME2,
                FILE_DESCRIPTION2, FILE_CONTENT_BYTES);
        given(homeworkService.findByIdWithFileContent(HOMEWORK_ID2)).willReturn(dto);
        byte[] returnedBody = mvc.perform(get("/homeworks/fileContent/" + HOMEWORK_ID2))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsByteArray();
        assertArrayEquals(FILE_CONTENT_BYTES, returnedBody);
    }

    @Test
    @WithMockUser
    public void canDeleteHomework() throws Exception {
        mvc.perform(delete("/homeworks/" + HOMEWORK_ID1))
                .andExpect(status().isOk());
    }

    @Ignore
    @Test
    @WithMockUser
    public void canPostHomework() throws Exception {
        var file = new MockMultipartFile("file", FILE_NAME2, MediaType.MULTIPART_FORM_DATA_VALUE, FILE_CONTENT_BYTES);
        var saveDto = new SaveHomeworkDto(GROUP_ID, TEACHER_ID, SUBJECT_ID, FILE_NAME2, FILE_DESCRIPTION2);
        var dtoWithoutFile = new HomeworkDtoWithoutFile(HOMEWORK_ID2, GROUP_ID, TEACHER_ID, SUBJECT_ID,
                FILE_NAME2, FILE_DESCRIPTION2);
        var dtoJson = new ObjectMapper().writeValueAsString(saveDto);
        var dtoFile = new MockMultipartFile("dto", "dto",
                MediaType.APPLICATION_JSON_UTF8_VALUE, dtoJson.getBytes());
        given(homeworkService.save(file,saveDto)).willReturn(dtoWithoutFile);
        mvc.perform(multipart("/homeworks")
                .file(file)
                .file(dtoFile)
                .accept(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groupId").value(GROUP_ID))
                .andExpect(jsonPath("$.teacherId").value(TEACHER_ID))
                .andExpect(jsonPath("$.subjectId").value(SUBJECT_ID))
                .andExpect(jsonPath("$.fileName").value(FILE_NAME1))
                .andExpect(jsonPath("$.fileDescription").value(FILE_DESCRIPTION2));
    }

    @Test
    public void unauthorizedUserCannotGetHomeworkWithoutFile() throws Exception {
        mvc.perform(get("/homeworks/" + HOMEWORK_ID1))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void unauthorizedUserCannotGetAllHomeworksWithoutFile() throws Exception {
        mvc.perform(get("/homeworks"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void unauthorizedUserCannotGetFileContent() throws Exception {
        mvc.perform(get("/homeworks/fileContent/" + HOMEWORK_ID2))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void unauthorizedUserCannotDeleteHomework() throws Exception {
        mvc.perform(delete("/homeworks/" + HOMEWORK_ID1))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void unauthorizedUserCannotPostHomework() throws Exception {
        var file = new MockMultipartFile("file", FILE_NAME2, MediaType.MULTIPART_FORM_DATA_VALUE, FILE_CONTENT_BYTES);
        var saveDto = new SaveHomeworkDto(GROUP_ID, TEACHER_ID, SUBJECT_ID, FILE_NAME2, FILE_DESCRIPTION2);
        var dtoJson = new ObjectMapper().writeValueAsString(saveDto);
        var dtoFile = new MockMultipartFile("dto", "dto",
                MediaType.APPLICATION_JSON_UTF8_VALUE, dtoJson.getBytes());
        mvc.perform(multipart("/homeworks")
                .file(file)
                .file(dtoFile)
                .accept(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(status().isUnauthorized());
    }

}
