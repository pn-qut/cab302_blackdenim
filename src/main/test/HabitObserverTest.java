import com.example.habittracker.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HabitObserverTest {
    private static final int USER_ID = 1;
    private static final LocalDate TODAY = LocalDate.of(2026, 10, 3);

    private final Habit drinkWater = new Habit(1, "Drink water");

    private HabitService habitService;
    private CountingObserver observer;

    @BeforeEach
    public void setUp() {
        IHabitEntryDAO entryDAO = new MockHabitEntryDAO();
        IUserHabitDAO userHabitDAO = new MockUserHabitDAO(List.of(drinkWater));
        habitService = new HabitService(entryDAO, userHabitDAO);
        observer = new CountingObserver();
    }

    @Test
    public void ObserverIsNotifiedWhenHabitCompleted() {
        habitService.attachObserver(observer);

        habitService.setHabitCompleted(USER_ID, drinkWater.getId(), TODAY, true);

        assertEquals(1, observer.timesNotified);
    }

    @Test
    public void ObserverIsNotifiedWhenHabitUncompleted() {
        habitService.attachObserver(observer);

        habitService.setHabitCompleted(USER_ID, drinkWater.getId(), TODAY, false);

        assertEquals(1, observer.timesNotified);
    }

    @Test
    public void ObserverIsNotNotifiedBeforeAnyChange() {
        habitService.attachObserver(observer);

        assertEquals(0, observer.timesNotified);
    }

    @Test
    public void DetachedObserverIsNotNotified() {
        habitService.attachObserver(observer);
        habitService.detachObserver(observer);

        habitService.setHabitCompleted(USER_ID, drinkWater.getId(), TODAY, true);

        assertEquals(0, observer.timesNotified);
    }

    @Test
    public void AllAttachedObserversAreNotified() {
        CountingObserver secondObserver = new CountingObserver();
        habitService.attachObserver(observer);
        habitService.attachObserver(secondObserver);

        habitService.setHabitCompleted(USER_ID, drinkWater.getId(), TODAY, true);

        assertEquals(1, observer.timesNotified);
        assertEquals(1, secondObserver.timesNotified);
    }

    private static class CountingObserver implements HabitObserver {
        private int timesNotified = 0;

        @Override
        public void onHabitsChanged() {
            timesNotified++;
        }
    }
}
