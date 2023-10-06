import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.example.StringCalculator;

public class StringCalculatorTest {
    @Test
    void testAddEmptyString() {
        StringCalculator calculator = new StringCalculator();
        assertEquals(0, calculator.add(""));
        assertEquals(1, calculator.add("1"));
        assertEquals(3, calculator.add("1,2"));
        assertEquals(53, calculator.add("1,2,4,6,8,10,22"));
        assertThrows(IllegalArgumentException.class, () -> calculator.add("1,g,3"));
        assertThrows(IllegalArgumentException.class, () -> calculator.add("1,,3"));
        assertThrows(IllegalArgumentException.class, () -> calculator.add(",1,3"));

    }
}

