import com.example.habittracker.model.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("Jerry", "Password12");
    }

    @Test
    public void testGetUsername() {
        assertEquals("Jerry", user.getUsername());
    }

    @Test
    public void testSetUsername() {
        user.setUsername("Jane");
        assertEquals("Jane", user.getUsername());
    }

    @Test
    public void testGetPassword() {
        assertEquals("Password12", user.getPassword());
    }
    @Test
    public void testSetPassword() {
        user.setPassword("Password25");
        assertEquals("Password25", user.getPassword());
    }

}
