import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class Ramsey2ColBounderTest {
    @Test
    public void boundRVsKnown() throws Exception {
        Ramsey2ColBounder r = new Ramsey2ColBounder();
        BigInteger six = new BigInteger("6");
        BigInteger nine = new BigInteger("9");
        BigInteger eighteen = new BigInteger("18");
        BigInteger twentyFive = new BigInteger("25");

        // R(3, 3) = 6, so bound >= 6
        assert r.boundR(3, 3).compareTo(six) >= 0;

        // R(3, 4) = R(4, 3) = 9 so bound >= 9
        BigInteger a = r.boundR(4, 3);
        assert r.boundR(4, 3).compareTo(nine) >= 0;
        assert r.boundR(4, 3).equals(r.boundR(3, 4));

        // R(4, 4) = 18 so bound >= 18
        assert r.boundR(4, 4).compareTo(eighteen) >= 0;

        // R(4, 5) = R(5, 4) = 25 so bound >= 25
        assert r.boundR(5, 4).compareTo(twentyFive) >= 0;
        assert r.boundR(5, 4).equals(r.boundR(4,5));
    }

    @Test
    public void boundRThm1() throws Exception {
        Ramsey2ColBounder r = new Ramsey2ColBounder();
        int NUM_ITERS = 25, a, b;
        for (int t= 0; t < NUM_ITERS; ++t) {
            //randomized testing to make sure R(a, b) = R(b, a)
            a = (int)(Math.random() * 8) + 1;
            b = (int)(Math.random() * 8) + 1;
            assert (r.boundR(a, b).equals(r.boundR(b, a)));
        }
    }

}