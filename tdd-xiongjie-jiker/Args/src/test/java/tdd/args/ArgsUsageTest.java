package tdd.args;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ArgsUsageTest {

    @Test
    public void testArgs() {
        String argsText = "-l -p 8080 -d /usr/logs";
        String schemaText = "l:bool p:int d:str";

        // 1. 把命令行字符串拆分成main函数可用的字符串数组
        String[] argsArr = new CLIInputParser().parse(argsText);

        // 2. 字符串形式的Schema解析成对象
        Schema schema = new Schema(schemaText);

        // 3. 根据Schema和参数字符串数组获取参数值
        Args args = new Args(schema, argsArr);
        assertEquals(args.getValue("l"), true);
        assertEquals(args.getValue("p"), 8080);
        assertEquals(args.getValue("d"), "/usr/logs");
    }

}
