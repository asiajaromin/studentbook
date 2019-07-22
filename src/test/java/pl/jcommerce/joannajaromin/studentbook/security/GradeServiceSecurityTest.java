package pl.jcommerce.joannajaromin.studentbook.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaGradeConverter;
import pl.jcommerce.joannajaromin.studentbook.dto.OrikaSaveGradeConverter;
import pl.jcommerce.joannajaromin.studentbook.repository.GradeRepository;
import pl.jcommerce.joannajaromin.studentbook.service.GradeService;
import pl.jcommerce.joannajaromin.studentbook.service.GradeServiceImpl;

import static org.mockito.Mockito.mock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class GradeServiceSecurityTest {

    private GradeService gradeService;
    private GradeRepository gradeRepository;
    private OrikaGradeConverter gradeConverter;
    private OrikaSaveGradeConverter saveGradeConverter;

    @Before
    public void before(){
        gradeRepository = mock(GradeRepository.class);
        gradeConverter = mock(OrikaGradeConverter.class);
        saveGradeConverter = mock(OrikaSaveGradeConverter.class);
        gradeService = new GradeServiceImpl(gradeRepository,gradeConverter,saveGradeConverter);
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void getMessageUnauthenticated() {
        gradeService.findAll();
    }


}
