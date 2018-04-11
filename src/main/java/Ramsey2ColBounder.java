import javafx.util.Pair;

import java.math.BigInteger;
import java.util.HashMap;

public class Ramsey2ColBounder {
    private HashMap<Pair<Integer, Integer>, BigInteger> memo;
    public Ramsey2ColBounder() {
        memo = new HashMap<Pair<Integer, Integer>, BigInteger>();
    }

    public BigInteger boundR(int a, int b) {
        Pair<Integer, Integer> tup = new Pair<Integer, Integer>(a, b);
        //check if previously computed
        if (memo.containsKey(tup)) {
            return memo.get(tup)
        }
        //compute a bound for R(a, b)
        //TODO implement this

    }
}
