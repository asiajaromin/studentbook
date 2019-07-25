import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import pl.jcommerce.joannajaromin.studentbook.entity.User
import pl.jcommerce.joannajaromin.studentbook.repository.UserRepository
import pl.jcommerce.joannajaromin.studentbook.service.UserService
import spock.lang.Specification

class CustomUserServiceTest extends Specification {

    private User user
    private int USER_ID = 12
    private String USERNAME = "Best User"
    private String INCORRECT_USERNAME = "Bad user"
    private String PASSWORD = "MySecretPassword"
    private String STUDENT_ROLE = "ROLE_STUDENT"
    private UserService userService

    def "UserService should return UserDetails based on username"() {
        given:
        def userRepository = Mock(UserRepository)
        user = new User(USER_ID, USERNAME, PASSWORD, STUDENT_ROLE)
        userService = new UserService(userRepository)
        userRepository.findByUsername(USERNAME) >> user

        when:
        UserDetails userFromService = userService.loadUserByUsername(USERNAME)

        then:
        userFromService.username == user.username
        userFromService.username == user.username
        userFromService.password == user.password
        userFromService.authorities.size() == 1
        userFromService.authorities[0].authority == user.authority
    }

    def "UserService should throw exception when user doesn't exist"() {
        given:
        def userRepository = Mock(UserRepository)
        userService = new UserService(userRepository)
        userRepository.findByUsername(INCORRECT_USERNAME) >> null

        when:
        UserDetails userFromService = userService.loadUserByUsername(INCORRECT_USERNAME)

        then:
        userFromService == null
        UsernameNotFoundException exception = thrown()
        exception.message == 'Brak użytkownika o nazwie: ' + INCORRECT_USERNAME
    }
}
