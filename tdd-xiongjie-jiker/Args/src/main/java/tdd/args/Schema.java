package tdd.args;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Schema {
    private Map<String, String> defPair;

    public Schema(String schemaText) {
        defPair = new HashMap<>();
        Arrays.stream(schemaText.split("\\s+"))
                .filter(str -> str.contains(":"))
                .map(str -> str.split(":"))
                .filter(arr -> arr.length == 2 && arr[0].trim().length() > 0 && arr[1].trim().length() > 0)
                .forEach(arr -> {
                    defPair.put(arr[0].trim(), arr[1].trim());
                });
    }

    public String getType(String param) {
        return defPair.get(param);
    }

}
