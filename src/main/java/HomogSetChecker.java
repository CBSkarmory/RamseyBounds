import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;
import org.jgrapht.alg.clique.BronKerboschCliqueFinder;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class HomogSetChecker {
    private static final int NUM_WORKERS = 4;

    public static boolean hasHomogSetSizeK(int[][] graph, int k) {
        return k <= findMaxHomogSetSize(graph);
    }

    public static int findMaxHomogSetSize(int[][] coloring) {
        int max = -1, n = coloring.length;
        for (int r = 0; r < n; ++r) {
            for (int c = r + 1 ; c < n; ++c) {
                if (coloring[r][c] > max) {
                    max = coloring[r][c];
                }
            }
        }
        return findMaxHomogSetSize(coloring, max);
    }

    public static int findMaxHomogSetSize(int[][] coloring, int maxCol) {
        Queue<Graph<Integer, DefaultEdge>> work= new LinkedList<>();
        for (int currCol = 0; currCol <= maxCol; ++currCol) {
            EdgeFactory<Integer, DefaultEdge> edgeFactory = new ClassBasedEdgeFactory<Integer, DefaultEdge>(DefaultEdge.class);
            Graph<Integer, DefaultEdge> g = new SimpleGraph<Integer, DefaultEdge>(edgeFactory);
            //build graph with edges connecting only if edge colored appropriately
            //vertices
            for (int i = 0; i < coloring.length; ++i) {
                g.addVertex(i + 1);
            }
            //edges
            for (int r = 0; r < coloring.length; ++r) {
                for (int c = 0; c < coloring[0].length; ++c) {
                    if (r == c) {
                        continue;
                    }
                    int color = coloring[r][c];
                    if (color == currCol) {
                        g.addEdge(r + 1, c + 1);
                    }
                }
            }
            work.offer(g);
        }

        ArrayList<Integer> results = new ArrayList<>();


        for (int i = 0; i < Math.min(NUM_WORKERS, maxCol + 1); ++i) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    boolean running = true;
                    while (running) {
                        Graph<Integer, DefaultEdge> g = null;
                        synchronized (work) {
                            if (!work.isEmpty()) {
                                g = work.poll();
                            } else {
                                running = false;
                            }
                        }
                        if (running) {
                            assert null != g;
                            BronKerboschCliqueFinder<Integer, DefaultEdge> finder = new BronKerboschCliqueFinder<>(g);
                            Iterator<Set<Integer>> it = finder.maximumIterator();
                            Set<Integer> clique1 = it.next();
                            int maxCliqueSize = clique1.size();
                            synchronized (results) {
                                results.add(maxCliqueSize);
                            }

                        }
                    }

                }
            };
            r.run();
        }

        while (results.size() < maxCol + 1) {
            //colors start at 0
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                System.err.println("interrupted");
                System.exit(-999);
            }
        }
        return Collections.max(results);
    }
}
