public class Runner {
    public static void main(String[] args) {
        boundNums();
        report10to20();
    }

    private static void boundNums() {
        Ramsey3ColBounder bounder = new Ramsey3ColBounder();
        final int N = 10;
        for (int a = 2; a <= N; ++a) {
            for (int b = a; b <= N; ++b) {
                for (int c = b; c <= N; ++c) {
                    System.out.printf("R(%d, %d, %d) <= %s\n", a, b, c, bounder.boundR(a, b, c).toString());
                }
            }
        }
    }

    private static void report10to20() {
        for (int n = 10; n <= 20; ++n) {
            int k = probMinMaxHomogSetSize(n) + 1;
            System.out.printf("n = %d: encountered case with no homog set size k = %d", n, k);
            if (k > n) {
                System.out.print(" -- no LARGE");
            }
            System.out.print("\n");
        }
    }

    private static int probMinMaxHomogSetSize(int n) {
        final int NUM_TRIALS = 100;
        int minMaxHomogSetSize = Integer.MAX_VALUE;
        for (int t = 0; t < NUM_TRIALS; ++t) {
            int[][] coloring = new int[n][n];
            for (int r = 0; r < n; ++r) {
                for (int c = r + 1 ; c < n; ++c) {
                    int randCol = (int) (Math.random() * 2);
                    coloring[r][c] = randCol;
                    coloring[c][r] = randCol;
                }
            }
            int maxHomogSetSize = HomogSetChecker.findMaxHomogSetSize(coloring);
            minMaxHomogSetSize = Math.min(maxHomogSetSize, minMaxHomogSetSize);
        }
        return minMaxHomogSetSize;
    }
}
