import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.*;

/**
 * This will obtain bounds on R(a, b, c) for 1 <= a <= b <= c <= 10
 * R(a, b, c) is least n such that for all 3-colorings of an n-clique,
 * there exists either
 * (1) RED a-clique
 * (2) BLUE b-clique
 * (3) GREEN c-clique
 */

public class Ramsey3ColBounder {
   private static final int MAX_C_VAL = 10;
   private HashMap<List<Integer>, BigInteger> memo;
   private final boolean SAFETY;

   private Ramsey2ColBounder twoColBounder;

   public Ramsey3ColBounder() {
       this(false);
   }

   public Ramsey3ColBounder(boolean allowLargerThan10) {
       SAFETY = allowLargerThan10;
       memo = new HashMap<List<Integer>, BigInteger>();
       twoColBounder = new Ramsey2ColBounder();
   }

   public BigInteger boundR(int a, int b, int c) {
       //order irrelevant, so force a <= b <= c
       if (a > b || b > c) {
           int[] tmp = new int[3];
           tmp[0] = a; tmp[1] = b; tmp[2] = c;
           Arrays.sort(tmp);
           a = tmp[0]; b = tmp[1]; c = tmp[2];
       }
       //assert c <= 10
       if (c > MAX_C_VAL && !SAFETY) {
           throw new IllegalArgumentException("c <= 10 constraint violated");
       }

       Integer[] tup = new Integer[3];
       tup[0] = a; tup[1] = b; tup[2] = c;
       List<Integer> tuple = Arrays.asList(tup);

       //check if previously computed
       if (memo.containsKey(tuple)) {
           return memo.get(tuple);
       }
       //compute a bound
       BigInteger val;

       if (tup[0] == 2 && tup[1] == 2) {
           //thm: R(2, 2, c) = c ... and symmetric cases
           val = new BigInteger("" + c);
       } else if (tup[0] == 2) {
           //thm: R(2, b, c) = R(b, c) ... and symmetric cases
           val = twoColBounder.boundR(b, c);
       } else {
           //thm: R(a, b, c) <= R(a-1, b, c) + R(a, b-1, c) + R(a, b, c-1)
           val = boundR(a - 1, b, c);
           val = val.add(boundR(a, b - 1, c));
           val = val.add(boundR(a, b, c - 1));
       }

       //memoize it
       memo.put(tuple, val);
       return val;
   }
}
