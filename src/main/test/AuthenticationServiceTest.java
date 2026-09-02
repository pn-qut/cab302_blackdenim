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



    @Test
    public void RegisterWithPasswordLessThan8CharsThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.register( "Tom", "Passw1" );
        });
    }

    @Test
    public void RegisterWithPasswordWithoutUppercaseCharThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.register( "Tom", "password1" );
        });
    }

    @Test
    public void RegisterWithPasswordWithoutLowercaseCharThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.register( "Tom", "PASSWORD1" );
        });
    }

    @Test
    public void RegisterWithPasswordWithoutNumberThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.register( "Tom", "Password" );
        });
    }

    @Test
    public void RegisterWithNullOrEmptyPasswordThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.register( "Tom", null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.register( "Tom", "");
        });
    }

    @Test
    public void RegisterWithNullOrEmptyUsernameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.register( null, "Password12");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.register( "", "Password12");
        });
    }

    @Test
    public void LoginWithNullOrEmptyPasswordThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.login( "Tom", null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.login( "Tom", "");
        });
    }

    @Test
    public void LoginWithNullOrEmptyUsernameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.login( null, "Password12");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.login( "", "Password12");
        });
    }

    @Test
    public void RegisterWithUsernameTooLongThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.login( "asdfghjklasdfghjklasd", "Password12");
        });
    }

    @Test
    public void RegisterWithPasswordTooLongThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.login( "Tom", "Asdfghjklasdfghjkla12");
        });
    }

    @Test
    public void RegisterWithUsernameContainingSpaceThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.login( "T om", "Password12");
        });
    }

    @Test
    public void RegisterWithPasswordContainingSpaceThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.login( "Tom", "Passw ord12");
        });
    }















    // Password must be at least 8 chars long
    // Contains at least one number, lowercase letter and uppercase letter
    // TODO: add all tests
}


