package tdd.args;

import java.util.Arrays;

public class CLIInputParser {

    public String[] parse(String argsText) {
        // todo 这里直接用 - 进行分割的，所以，无法支持参数值为负数的情况
        return Arrays.stream(argsText.split("-"))
                .map(String::trim)
                .filter(str -> str.length() > 0)
                .toArray(String[]::new);
    }

}
