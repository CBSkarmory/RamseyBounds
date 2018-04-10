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
   private HashMap<List<Integer>, BigInteger> memo;
   private final boolean SAFTEY;

   public Ramsey3ColBounder() {
       this(false);
   }

   public Ramsey3ColBounder(boolean allowLargerThan10) {
       SAFTEY = allowLargerThan10;
       memo = new HashMap<List<Integer>, BigInteger>();
   }

   public BigInteger boundR(int a, int b, int c) {
       //assert a <= b <= c
       if (a > b || b > c) {
           throw new IllegalArgumentException("a <= b <= c constraint violated");
       }
       //assert c <= 10
       if (c > 10 && !SAFTEY) {
           throw new IllegalArgumentException("c <= 10 constraint violated");
       }

       Integer[] tup = new Integer[3];
       tup[0] = a; tup[1] = b; tup[2] = c;
       List<Integer> tuple = Arrays.asList(tup);

       //check if previously computed
       if (memo.containsKey(tuple)) {
           return memo.get(tuple);
       } else {
           //compute a bound
           //TODO
           //memoize it
           //TODO
       }
   }
}
