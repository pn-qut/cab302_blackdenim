import com.example.habittracker.model.Habit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class SqliteCommunityHabitDAOTest {

    private SqliteCommunityHabitDAO habitDAO;

    @BeforeEach
    void setUp() {
        habitDAO = new SqliteCommunityHabitDAO();
    }

    @Test
    void getCommunityHabitTest() {

        int postcode = 4000;
        String expectedName = "TestHabit";
        Habit communityhabit = habitDAO.getCommunityHabit(postcode);

        assertNotNull(communityhabit);
        assertEquals(expectedName, communityhabit.getName());
    }

    @Test
    void getDailyCommunityCompletionsTest() {
        int postcode = 4000;
        LocalDate today = LocalDate.of(2026,10,22);
        int expectedTestResult = 2;

        int totalCompletions = habitDAO.getTotalCommunityCompletions(postcode, today);

        assertEquals(expectedTestResult, totalCompletions);
    }

}