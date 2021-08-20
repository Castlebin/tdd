package tdd;

/**
 * @author gaoyuxin <gaoyuxin@kuaishou.com>
 * Created on 2021-08-20
 */
public class GameNumber {
    private final int rawNumber;

    public GameNumber(int rawNumber) {
        this.rawNumber = rawNumber;
    }

    public String fizzBuzz() {
        if (isRelatedTo(3) && isRelatedTo(5)) {
            return "fizzbuzz";
        }
        if (isRelatedTo(3)) {
            return "fizz";
        }
        if (isRelatedTo(5)) {
            return "buzz";
        }
        return String.valueOf(rawNumber);
    }

    private boolean isRelatedTo(int num) {
        return rawNumber % num == 0
                || String.valueOf(rawNumber).contains(String.valueOf(num));
    }

}
