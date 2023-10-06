import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.example.StringCalculator;
public class StringCalculatorTest {

    @Test
    public void testAddEmptyString() {
        StringCalculator calculator = new StringCalculator();
        int result = calculator.add("");
        assertEquals(0, result);
    }

    @Test
    public void testAddSingleNumber() {
        StringCalculator calculator = new StringCalculator();
        int result = calculator.add("1");
        assertEquals(1, result);
    }

    @Test
    public void testAddTwoNumbers() {
        StringCalculator calculator = new StringCalculator();
        int result = calculator.add("1,2");
        assertEquals(3, result);
    }
    @Test
    public void testAddInvalidInput() {
        StringCalculator calculator = new StringCalculator();
        assertThrows(IllegalArgumentException.class, () -> calculator.add("1,g,3"));
        assertThrows(IllegalArgumentException.class, () -> calculator.add("1,,3"));
        assertThrows(IllegalArgumentException.class, () -> calculator.add(",1,3"));
        assertThrows(IllegalArgumentException.class, () -> calculator.add("1,1,3"));
    }
}
