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
    private UserRepository userRepository

    def setup(){
        userRepository = Mock()
        userService = new UserService(userRepository)
    }

    def "UserService should return UserDetails based on username"() {
        given:
        user = new User(USER_ID, USERNAME, PASSWORD, STUDENT_ROLE)
        userRepository.findByUsername(USERNAME) >> user

        when:
        UserDetails userFromService = userService.loadUserByUsername(USERNAME)

        then:
        userFromService.username == user.username
        userFromService.password == user.password
        userFromService.authorities.size() == 1
        userFromService.authorities[0].authority == user.authority
        userFromService.accountNonExpired == true
        userFromService.accountNonLocked == true
        userFromService.credentialsNonExpired == true
        userFromService.enabled == true
    }

    def "UserService should throw exception when user doesn't exist"() {
        given:

        when:
        userService.loadUserByUsername(INCORRECT_USERNAME)

        then:
        UsernameNotFoundException exception = thrown()
        exception.message == 'Brak użytkownika o nazwie: ' + INCORRECT_USERNAME
    }
}
