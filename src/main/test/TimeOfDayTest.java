import com.example.habittracker.model.TimeOfDay;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeOfDayTest {

    @Test
    public void GreetingIsMorningAtMidnight() {
        assertEquals("Morning", TimeOfDay.greetingFor(LocalTime.MIDNIGHT));
    }

    @Test
    public void GreetingIsMorningJustBeforeNoon() {
        assertEquals("Morning", TimeOfDay.greetingFor(LocalTime.of(11, 59)));
    }

    @Test
    public void GreetingIsAfternoonAtNoon() {
        assertEquals("Afternoon", TimeOfDay.greetingFor(LocalTime.NOON));
    }

    @Test
    public void GreetingIsAfternoonJustBeforeSix() {
        assertEquals("Afternoon", TimeOfDay.greetingFor(LocalTime.of(17, 59)));
    }

    @Test
    public void GreetingIsEveningAtSix() {
        assertEquals("Evening", TimeOfDay.greetingFor(LocalTime.of(18, 0)));
    }

    @Test
    public void GreetingIsEveningJustBeforeMidnight() {
        assertEquals("Evening", TimeOfDay.greetingFor(LocalTime.of(23, 59)));
    }
}
