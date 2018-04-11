import org.jgrapht.alg.util.Pair;

import java.math.BigInteger;
import java.util.HashMap;

/**
 * This will obtain bounds on R(a, b) for 1 <= a <= b <= 10
 * R(a, b) is least n such that for all 2-colorings of an n-clique,
 * there exists either
 * (1) RED a-clique
 * (2) BLUE b-clique
 */

public class Ramsey2ColBounder {
    private static final int MAX_B_VAL = 10;
    private final boolean SAFETY;
    private HashMap<Pair<Integer, Integer>, BigInteger> memo;

    public Ramsey2ColBounder() {
        this(false);
    }
    public Ramsey2ColBounder(boolean allowLargerThan10) {
        SAFETY = allowLargerThan10;
        memo = new HashMap<Pair<Integer, Integer>, BigInteger>();
    }

    public BigInteger boundR(int a, int b) {
        //order irrelevant, so force a <= b
        if (a > b) {
            return boundR(b, a);
        }
        //assert b <= 10
        if (b > MAX_B_VAL && !SAFETY) {
            throw new IllegalArgumentException("b <= 10 constraint violated");
        }

        Pair<Integer, Integer> tup = new Pair<Integer, Integer>(a, b);
        //check if previously computed
        if (memo.containsKey(tup)) {
            return memo.get(tup);
        }
        //compute a bound for R(a, b)
        BigInteger val;
        if (a == 2) {
            //thm: R(a, b) = b
            val = new BigInteger("" + b);
        } else {
            //thm: R(a, b) <= R(a - 1, b) + R(a, b - 1)
            val = boundR(a - 1, b);
            val = val.add(boundR(a, b - 1));
        }
        memo.put(tup, val);
        return val;
    }
}
