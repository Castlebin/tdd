package tdd;

/**
 任务需求：
 1. 打印出从 1 到 100 的数字，
 2. 将其中 3 的倍数替换成 “Fizz”，
 3. 将 5 的倍数替换成 “Buzz”，
 4. 将既能被 3 整除、又能被 5 整除的数则替换成 “FizzBuzz”。

 其他要求：
 1. 代码整洁，没有重复代码
 2. 有单元测试，单元测试覆盖率100%
 3. 10分钟内完成
 */
public class FizzBuzzVersion1 {

    public static final String fizzBuzz(int num) {
        if (num % 15 == 0) {
            return "FizzBuzz";
        }
        if (num % 3 == 0) {
            return "Fizz";
        }
        if (num % 5 == 0) {
            return "Buzz";
        }
        return String.valueOf(num);
    }

}
