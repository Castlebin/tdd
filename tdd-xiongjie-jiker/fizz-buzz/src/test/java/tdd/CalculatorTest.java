package tdd;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CalculatorTest {

    @Test
    public void testAdd() {
        assertEquals(new Calculator().add(1,2), 3);
        assertEquals(new Calculator().add(2,7), 9);
    }

}
