import com.example.habittracker.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AuthenticationServiceTest {
    private final User user = new User("John43", "password12", 4000);
    private AuthenticationService authenticationService;
    IUserDAO userDAO = new MockUserDAO();
    private User[] users = {
            new User("John43", "password12", 4000),
            new User("Jane", "test14", 4000),
            new User("Jay", "Doe", 4000),
            new User("Alice", "Testpw12", 4000),
            new User("Tom", "G352", 4000)
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
        authenticationService.register("Sam", "Testjh14", 4000);
        User user = authenticationService.login("Sam", "Testjh14");

        assertEquals("Sam", user.getUsername());
        assertEquals("Testjh14", user.getPassword());
        assertEquals(4000, user.getPostcode());
    }

    @Test
    public void RegisterWithTakenUsernameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.register("Jay", "pwdfbd", 4000);
        });
    }

    @Test
    public void RegisterWithPostcodeNotInLookupThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.register("Sam", "Testjh14", 4999);
        });
    }

    @Test
    public void GetSuburbsByPostcodeReturnsLookupResults() {
        assertEquals(java.util.Collections.singletonList("Test Suburb"),
                authenticationService.getSuburbsByPostcode(4000));
    }

    @Test
    public void RegisterWithPasswordLessThan8CharsThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.register( "Tom", "Passw1", 4000 );
        });
    }

    @Test
    public void RegisterWithPasswordWithoutUppercaseCharThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.register( "Tom", "password1", 4000 );
        });
    }

    @Test
    public void RegisterWithPasswordWithoutLowercaseCharThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.register( "Tom", "PASSWORD1", 4000 );
        });
    }

    @Test
    public void RegisterWithPasswordWithoutNumberThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.register( "Tom", "Password", 4000 );
        });
    }

    @Test
    public void RegisterWithNullOrEmptyPasswordThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.register( "Tom", "", 4000);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.register( "Tom", null, 4000);
        });
    }

    @Test
    public void RegisterWithNullOrEmptyUsernameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.register( null, "Password12", 4000);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.register( "", "Password12", 4000);
        });
    }

    @Test
    public void RegisterWithUsernameTooLongThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.register( "asdfghjklasdfghjklasd", "Password12", 4000);
        });
    }

    @Test
    public void RegisterWithPasswordTooLongThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.register( "Tom", "Asdfghjklasdfghjkla12", 4000);
        });
    }

    @Test
    public void RegisterWithUsernameContainingSpaceThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.register( "T om", "Password12", 4000);
        });
    }

    @Test
    public void RegisterWithPasswordContainingSpaceThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.register( "Tom", "Passw ord12", 4000);
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
    @Test
    public void ChangePasswordWithValidDetailsAllowsLoginWithNewPassword() {
        authenticationService.changePassword("Alice", "Testpw12", "Newpass34");
        User user = authenticationService.login("Alice", "Newpass34");

        assertEquals("Alice", user.getUsername());
        assertEquals("Newpass34", user.getPassword());
    }

    @Test
    public void ChangePasswordThenLoginWithOldPasswordThrowsException() {
        authenticationService.changePassword("Alice", "Testpw12", "Newpass34");

        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.login("Alice", "Testpw12");
        });
    }

    @Test
    public void ChangePasswordWithIncorrectCurrentPasswordThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.changePassword("Alice", "Wrongpw12", "Newpass34");
        });
        // The original password must still work after a failed change
        assertEquals("Testpw12", authenticationService.login("Alice", "Testpw12").getPassword());
    }

    @Test
    public void ChangePasswordForUnregisteredUserThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.changePassword("Borris", "Testpw12", "Newpass34");
        });
    }

    @Test
    public void ChangePasswordToPasswordWithoutNumberThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.changePassword("Alice", "Testpw12", "Password");
        });
    }

    @Test
    public void ChangePasswordToSamePasswordThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.changePassword("Alice", "Testpw12", "Testpw12");
        });
    }
}
