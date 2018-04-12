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

    public static boolean hasHomogSetSizek(int[][] graph, int k) {
        return k <= findMaxHomogSetSize(graph, k);
    }

    public static int findMaxHomogSetSize(int[][] graph, int k) {
        int max = -1;
        for (int[] row : graph) {
            for (int i : row) {
                if (i > max) {
                    max = i;
                }
            }
        }
        return findMaxHomogSetSize(graph, k, max);
    }

    public static int findMaxHomogSetSize(int[][] graph, int k, int maxCol) {
        Queue<Graph<Integer, DefaultEdge>> work= new LinkedList<>();
        for (int currCol = 0; currCol <= maxCol; ++currCol) {
            EdgeFactory<Integer, DefaultEdge> edgeFactory = new ClassBasedEdgeFactory<Integer, DefaultEdge>(DefaultEdge.class);
            Graph<Integer, DefaultEdge> g = new SimpleGraph<Integer, DefaultEdge>(edgeFactory);
            //build graph with edges connecting only if edge colored appropriately
            for (int r = 0; r < graph.length; ++r) {
                for (int c = 0; c < graph[0].length; ++c) {
                    if (r == c) {
                        continue;
                    }
                    int color = graph[r][c];
                    if (color == currCol) {
                        g.addEdge(r, c);
                    }
                }
            }
            work.offer(g);
        }

        ArrayList<Integer> results = new ArrayList<>();


        for (int i = 0; i < NUM_WORKERS; ++i) {
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
