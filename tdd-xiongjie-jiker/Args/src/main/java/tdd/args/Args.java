package tdd.args;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Args {
    private Schema schema;
    private Map<String, String> paramValueMap;

    public Args(Schema schema, String[] argsArr) {
        this.schema = schema;
        parseArgsArr(argsArr);
    }

    private void parseArgsArr(String[] argsArr) {
        paramValueMap = new HashMap<>();
        Arrays.stream(argsArr)
                .forEach(str -> {
                    String[] split = str.split("\\s+");
                    if (split.length == 1 || split[1].trim().length() == 0) {
                        paramValueMap.put(split[0], null);
                    } else {
                        paramValueMap.put(split[0], split[1]);
                    }
                });
    }

    public Object getValue(String param) {
        String type = schema.getType(param);
        if (type == null) {
            return null;
        }
        String value = paramValueMap.get(param);
        switch (type) {
            case "int":
                return value == null ? 0 : Integer.parseInt(value);
            case "bool":
                return value == null ? Boolean.TRUE : Boolean.valueOf(value);
            default:
                return value;
        }
    }

}
