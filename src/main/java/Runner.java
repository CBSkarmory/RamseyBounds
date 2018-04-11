import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;
import org.jgrapht.alg.clique.BronKerboschCliqueFinder;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.Iterator;
import java.util.Set;

public class Runner {
    public static void main(String[] args) {
/*        // testing
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
        System.out.println(clique1);*/

        // bounds on numbers
        Ramsey3ColBounder bounder = new Ramsey3ColBounder();
        int N = 10;
        for (int a = 2; a <= N; ++a) {
            for (int b = a; b <= N; ++b) {
                for (int c = b; c <= N; ++c) {
                    System.out.printf("R(%d, %d, %d) <= %s\n", a, b, c, bounder.boundR(a, b, c).toString());
                }
            }
        }
    }
}
