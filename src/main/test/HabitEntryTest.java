import com.example.habittracker.model.HabitEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HabitEntryTest {
    private HabitEntry habitEntry;

    @BeforeEach
    public void setUp() {
        habitEntry = new HabitEntry(
                1,
                "Jerry",
                2,
                LocalDate.of(2026, 9, 14),
                true
        );
    }

    @Test
    public void testGetters() {
        assertEquals(1, habitEntry.getId());
        assertEquals("Jerry", habitEntry.getUsername());
        assertEquals(2, habitEntry.getHabit_id());
        assertEquals(LocalDate.of(2026, 9, 14), habitEntry.getDate());
        assertTrue(habitEntry.isCompleted());
    }

    @Test
    public void testSetId() {
        habitEntry.setId(10);
        assertEquals(10, habitEntry.getId());
    }

    @Test
    public void testSetUsername() {
        habitEntry.setUsername("Jane");
        assertEquals("Jane", habitEntry.getUsername());
    }

    @Test
    public void testSetHabitId() {
        habitEntry.setHabit_id(20);
        assertEquals(20, habitEntry.getHabit_id());
    }

    @Test
    public void testSetDate() {
        LocalDate newDate = LocalDate.of(2026, 9, 15);
        habitEntry.setDate(newDate);
        assertEquals(newDate, habitEntry.getDate());
    }

    @Test
    public void testSetCompleted() {
        habitEntry.setCompleted(false);
        assertFalse(habitEntry.isCompleted());
    }
}
