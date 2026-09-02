import com.example.habittracker.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AuthenticationServiceTest {
    private final User user = new User("John43", "password12");
    private AuthenticationService authenticationService;
    IUserDAO userDAO = new MockUserDAO();
    private User[] users = {
            new User("John43", "password12"),
            new User("Jane", "test14"),
            new User("Jay", "Doe"),
            new User("Alice", "12345"),
            new User("Shane", "G352")
    };

    @BeforeEach
    public void setUp() {
        authenticationService = new AuthenticationService(userDAO);
    }

    @Test
    public void RegisterWithTakenUsernameThrowsException() {
        for (User user : users) {
            userDAO.addUser(user);
        }

        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.register("Jay", "pwdfbd");
        });
    }
    @Test
    public void testUsernameMatch() {

        assertEquals("John43", user.getUsername());
    }

    @Test
    public void testPasswordMatch() {

        assertEquals("password12", user.getPassword());
    }



    // TODO: add all tests
}


