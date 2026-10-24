import com.example.habittracker.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class AchievementServiceTest {
    private IHabitEntryDAO habitEntryDAO;
    private AchievementService service;

    @BeforeEach
    public void setUp() {
        habitEntryDAO = new MockHabitEntryDAO();
        service = new AchievementService(habitEntryDAO);
    }

    private long unlockedCount(List<Achievement> list) {
        return list.stream().filter(Achievement::isUnlocked).count();
    }

    @Test
    public void ZeroLogsUnlocksNothing() {
        List<Achievement> result = service.getAchievements(0);
        assertEquals(3, result.size());
        assertEquals(0, unlockedCount(result));
    }

    @Test
    public void OneLogUnlocksFirstStepOnly() {
        List<Achievement> result = service.getAchievements(1);
        assertEquals(1, unlockedCount(result));
        assertTrue(result.get(0).isUnlocked());
    }

    @Test
    public void TenLogsUnlocksTwoBadges() {
        assertEquals(2, unlockedCount(service.getAchievements(10)));
    }

    @Test
    public void ProgressIsCappedAtTarget() {
        Achievement first = service.getAchievements(99).get(0);
        assertEquals(first.getTarget(), first.getProgress());
    }

    @Test
    public void UserWithNoCompletionsHasNothingUnlocked() {
        List<Achievement> result = service.getAchievementsForUser(1);
        assertEquals(3, result.size());
        assertEquals(0, unlockedCount(result));
    }

    @Test
    public void CompletionsAreCountedForTheUser() {
        for (int i = 0; i < 10; i++) {
            habitEntryDAO.completeHabit(1, 1, LocalDate.now().minusDays(i));
        }
        assertEquals(2, unlockedCount(service.getAchievementsForUser(1)));
    }

    @Test
    public void CompletionsAcrossDifferentHabitsAreAllCounted() {
        for (int i = 0; i < 5; i++) {
            habitEntryDAO.completeHabit(1, 1, LocalDate.now().minusDays(i));
            habitEntryDAO.completeHabit(1, 2, LocalDate.now().minusDays(i));
        }
        assertEquals(2, unlockedCount(service.getAchievementsForUser(1)));
    }

    @Test
    public void OtherUsersCompletionsAreNotCounted() {
        habitEntryDAO.completeHabit(2, 1, LocalDate.now());
        assertEquals(0, unlockedCount(service.getAchievementsForUser(1)));
    }
}