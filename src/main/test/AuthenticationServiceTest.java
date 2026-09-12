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
            new User("Alice", "Testpw12"),
            new User("Tom", "G352")
    };

    @BeforeEach
    public void setUp() {
        authenticationService = new AuthenticationService(userDAO);
        for (User user : users) {
            userDAO.addUser(user);
        }
    }

    @Test
    public void RegisterAndLoginWithValidUsernameAndPassword(){
        authenticationService.register("Sam", "Testjh14");
        User user = authenticationService.login("Sam", "Testjh14");

        assertEquals("Sam", user.getUsername());
        assertEquals("Testjh14", user.getPassword());
    }

    @Test
    public void RegisterWithTakenUsernameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.register("Jay", "pwdfbd");
        });
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
            authenticationService.register( "Tom", "");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.register( "Tom", null);
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
    public void RegisterWithUsernameTooLongThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.register( "asdfghjklasdfghjklasd", "Password12");
        });
    }

    @Test
    public void RegisterWithPasswordTooLongThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.register( "Tom", "Asdfghjklasdfghjkla12");
        });
    }

    @Test
    public void RegisterWithUsernameContainingSpaceThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.register( "T om", "Password12");
        });
    }

    @Test
    public void RegisterWithPasswordContainingSpaceThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.register( "Tom", "Passw ord12");
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
    public void LoginWithUnregisteredUsernameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.login( "Borris", "Password12");
        });
    }

    @Test
    public void LoginWithIncorrectPasswordThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.login( "Alice", "Password12");
        });
    }
}


