import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;
import org.jgrapht.alg.clique.BronKerboschCliqueFinder;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.Iterator;
import java.util.Set;

/**
 * This will obtain bounds on R(a, b, c) for 1 <= a <= b <= c <= 10
 * R(a, b, c) is least n such that for all 3-colorings of an n-clique,
 * there exists either
 * (1) RED a-clique
 * (2) BLUE b-clique
 * (3) GREEN c-clique
 */

public class Bounds3Col {
    public static void main(String[] args) {
        // testing
        EdgeFactory<Integer, DefaultEdge> edgeFactory = new ClassBasedEdgeFactory<Integer, DefaultEdge>(DefaultEdge.class);
        Graph<Integer, DefaultEdge> g = new SimpleGraph<Integer, DefaultEdge>(edgeFactory);

        int N = 2;
        //create clique of size N
        for (int i = 1; i <= N; ++i) {
            g.addVertex(i);
            for (Integer v : g.vertexSet()) {
                if (i != v) {
                    g.addEdge(i, v);
                }
            }
        }
        BronKerboschCliqueFinder<Integer, DefaultEdge> finder = new BronKerboschCliqueFinder<Integer, DefaultEdge>(g);
        Iterator<Set<Integer>> it = finder.maximumIterator();
        Set<Integer> clique1 = it.next();
        System.out.println(clique1);
    }
}
