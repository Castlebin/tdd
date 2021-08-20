package tdd;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**

 TDD 第一原则：先写测试

 */
public class GameNumberTest {

    @Test
    public void shouldReturnRawNumberForNormalNumbers() {
        checkFizzBuzz(1, "1");
        checkFizzBuzz(2, "2");
        checkFizzBuzz(4, "4");
    }

    private void checkFizzBuzz(int rawNumber, String showWord) {
        assertEquals(new GameNumber(rawNumber).fizzBuzz(), showWord);
    }

    @Test
    public void shouldReturnFizzIfRawNumberIsDivisileBy3() {
        checkFizzBuzz(3, "fizz");
        checkFizzBuzz(6, "fizz");
    }

    @Test
    public void shouldReturnFizzIfRawNumberContains3() {
        checkFizzBuzz(13, "fizz");
        checkFizzBuzz(31, "fizz");
    }

    @Test
    public void shouldReturnBuzzIfRawNumberIsDivisibleBy5() {
        checkFizzBuzz(5, "buzz");
        checkFizzBuzz(10, "buzz");
    }

    @Test
    public void shouldReturnBuzzIfRawNumberContains5() {
        checkFizzBuzz(56, "buzz");
        checkFizzBuzz(151, "buzz");
    }

    @Test
    public void shouldReturnFizzBuzzIfRawNumberIsDivisibleBy3And5() {
        checkFizzBuzz(15, "fizzbuzz");
        checkFizzBuzz(30, "fizzbuzz");
    }

    @Test
    public void shouldReturnFizzBuzzIfRawNumberContains3And5() {
        checkFizzBuzz(53, "fizzbuzz");
    }

}
