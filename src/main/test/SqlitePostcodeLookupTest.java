import com.example.habittracker.model.SqliteUserDAO;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SqlitePostcodeLookupTest {
    @Test
    void findsSuburbsFromBundledPostcodeDatabase() throws SQLException {
        try (Connection userDatabase = DriverManager.getConnection("jdbc:sqlite::memory:")) {
            SqliteUserDAO userDAO = new SqliteUserDAO(userDatabase);

            List<String> suburbs = userDAO.findSuburbsByPostcode(4000);

            assertTrue(suburbs.contains("BRISBANE"));
            assertTrue(userDAO.findSuburbsByPostcode(3999).isEmpty());
        }
    }
}
