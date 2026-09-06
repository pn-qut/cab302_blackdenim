import com.example.habittracker.database.DatabaseManager;
import com.example.habittracker.model.SqliteUserDAO;
import com.example.habittracker.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SqliteUserDAOTest {

    @TempDir
    Path tempDir;

    private SqliteUserDAO userDAO;

    @BeforeEach
    void setUp() {
        String dbPath = tempDir.resolve("test.db").toString();
        userDAO = new SqliteUserDAO(new DatabaseManager(dbPath));
    }

    @Test
    void addUserThenFindByUsernameReturnsMatchingUser() {
        userDAO.addUser(new User("alice", "password123"));

        User found = userDAO.findByUsername("alice");

        assertNotNull(found);
        assertEquals("alice", found.getUsername());
        assertEquals("password123", found.getPassword());
    }

    @Test
    void usernameExistsReturnsTrueForKnownUserAndFalseForUnknown() {
        userDAO.addUser(new User("bob", "hunter2"));

        assertTrue(userDAO.usernameExists("bob"));
        assertFalse(userDAO.usernameExists("nobody"));
    }

    @Test
    void findByUsernameReturnsNullForUnknownUser() {
        assertNull(userDAO.findByUsername("nobody"));
    }
}
