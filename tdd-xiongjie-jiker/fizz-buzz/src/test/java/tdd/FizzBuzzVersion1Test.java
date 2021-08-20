package tdd;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * 测试驱动开发，推荐的是 ** 先写测试 **
 */
class FizzBuzzVersion1Test {

    @ParameterizedTest(name = "输入 {0} 应该返回 {1}")
    @CsvSource({
            "1, '1'",
            "3, 'Fizz'",
            "5, 'Buzz'",
            "15, 'FizzBuzz'",
    })
    public void fizzBuzzTest(int num, String word) {
        assertEquals(FizzBuzzVersion1.fizzBuzz(num), word);
    }

}
