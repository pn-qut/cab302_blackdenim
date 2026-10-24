import com.example.habittracker.model.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class AchievementServiceTest {
    private final AchievementService service = new AchievementService();

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
}