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
        assertEquals(4, calculator.add("1\n3"));
        assertEquals(6, calculator.add("1,3\n2"));
        assertThrows(IllegalArgumentException.class, () -> calculator.add("1\n\n3"));
        assertThrows(IllegalArgumentException.class, () -> calculator.add("\n1,3"));
        assertEquals(10, calculator.add("//;\n1;2;3;4"));
        assertEquals(10, calculator.add("//\n\n1,2\n3\n4"));
        assertEquals(10, calculator.add("//;\n1;2\n3,4"));
        assertThrows(IllegalArgumentException.class, () -> calculator.add("-1"));
        assertThrows(IllegalArgumentException.class, () -> calculator.add("1,-2,3"));
        assertThrows(IllegalArgumentException.class, () -> calculator.add("//;\n-1;2;-3;4"));
        assertEquals(1001, calculator.add("//*\n1000*1004*1234*1"));
        assertEquals(0, calculator.add("10004,1005,1234"));
    }
}

