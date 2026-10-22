import com.example.habittracker.model.Habit;
import com.example.habittracker.model.SqliteCommunityHabitDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class SqliteCommunityHabitDAOTest {

    private SqliteCommunityHabitDAO communityHabitDAO;

    @BeforeEach
    void setUp() {
        communityHabitDAO = new SqliteCommunityHabitDAO();
    }

    @Test
    void getCommunityHabitIDTest() {

        int postcode = 0;
        int expectedHabitID = 17;
        Integer communityhabit = communityHabitDAO.getCommunityHabitID(postcode);

        assertNotNull(communityhabit);
        assertEquals(expectedHabitID, communityhabit);
    }

    @Test
    void getDailyCommunityCompletionsTest() {
        int postcode = 0;
        LocalDate today = LocalDate.of(2026,10,15);
        int expectedTestResult = 1;

        int totalCompletions = communityHabitDAO.getTotalCommunityCompletions(postcode, today);

        assertEquals(expectedTestResult, totalCompletions);
    }

}