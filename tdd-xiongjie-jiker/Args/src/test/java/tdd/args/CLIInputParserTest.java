package tdd.args;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class CLIInputParserTest {

    @Test
    public void testParse() {
        String argsText = "-l -p 8080 -d /usr/logs";

        // 把命令行字符串拆分成main函数可用的字符串数组
        String[] argsArr = new CLIInputParser().parse(argsText);
        assertArrayEquals(new String[] {"l", "p 8080", "d /usr/logs"}, argsArr);
    }

}
