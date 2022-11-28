/**
 * Author: Myo Aung & Jihun Choi:
 * Class: CS3310-02 - Project2 Fall-2022
 * Test.java
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Test {
    public final int MAX_WEIGHT = 100;
    ArrayList<long[]> times = new ArrayList<>();

    public void runSanityChecks() {
        Graph testGraph = new Graph(
                new int[][]{
                        {0, 5, 11, 8},
                        {MAX_WEIGHT + 1, 0, MAX_WEIGHT + 1, 2},
                        {MAX_WEIGHT + 1, 10, 0, MAX_WEIGHT + 1},
                        {MAX_WEIGHT + 1, MAX_WEIGHT + 1, 1, 0},
                }
        );
        int[][] testGraphCostPrev0 = {
                {0, 5, 8, 7},
                {0, 0, 3, 1}
        };
        int[][] testGraphCostPrev1 = {
                {MAX_WEIGHT + 1, 0, 3, 2},
                {1, 1, 3, 1}
        };
        int[][] testGraphCostPrev2 = {
                {MAX_WEIGHT + 1, 10, 0, 12},
                {2, 2, 2, 1}
        };
        int[][] testGraphCostPrev3 = {
                {MAX_WEIGHT + 1, 11, 1, 0},
                {3, 2, 3, 3}
        };

        Dijkstras dijkstras = new Dijkstras(testGraph);
        dijkstras.findCheapestPath(0);
        if (!Arrays.deepEquals(dijkstras.costPrevTable, testGraphCostPrev0)) fail();

        dijkstras.findCheapestPath(1);
        if (!Arrays.deepEquals(dijkstras.costPrevTable, testGraphCostPrev1)) fail();

        dijkstras.findCheapestPath(2);
        if (!Arrays.deepEquals(dijkstras.costPrevTable, testGraphCostPrev2)) fail();

        dijkstras.findCheapestPath(3);
        if (!Arrays.deepEquals(dijkstras.costPrevTable, testGraphCostPrev3)) fail();


        System.out.println("Sanity Check Passed!");
        int[][] floydMatrix = {
                {0, 5, 8, 7},
                {MAX_WEIGHT + 1, 0, 3, 2},
                {MAX_WEIGHT + 1, 10, 0, 12},
                {MAX_WEIGHT + 1, 11, 1, 0},
        };
        FloydWarshall floyd = new FloydWarshall();
        int[][] matrix = floyd.getDistance(testGraph);

        if (!Arrays.deepEquals(matrix, floydMatrix)) fail();
        System.out.println("Sanity Check Passed!");
    }

    private Graph generateGraph(int nodes) {
        Random random = new Random(System.currentTimeMillis());
        int[][] adjacency = new int[nodes][nodes];
        for (int i = 0; i < nodes; i++) {
            for (int j = 0; j < nodes; j++) {
                if (i == j) {
                    adjacency[i][j] = 0;
                    continue;
                }
                boolean randomConnected = random.nextBoolean();
                if (!randomConnected) {
                    adjacency[i][j] = MAX_WEIGHT + 1;
                    continue;
                }
                int randomWeight = random.nextInt(MAX_WEIGHT);
                adjacency[i][j] = randomWeight;
            }
        }
        int randomNode = random.nextInt(nodes);
        for (int i = 0; i < nodes; i++) {
            if (i == randomNode)
                continue;
            if (adjacency[randomNode][i] < MAX_WEIGHT + 1)
                continue;
            int randomWeight = random.nextInt(MAX_WEIGHT);
            adjacency[randomNode][i] = randomWeight;
        }
        return new Graph(adjacency);
    }

    private long timer(Runnable r) {
        long s = System.currentTimeMillis();
        r.run();
        long e = System.currentTimeMillis();
        return e - s;
    }

    public void runTests(int runs, int nodeTwoPow) {
        for (int j = 1; j < nodeTwoPow + 1; j++) {
            int nodes = (int) Math.round(Math.pow(2, j));
            long timesDijkstra = 0;
            long timesFloyd = 0;
            for (int i = 1; i < runs + 1; i++) {
                System.out.println();
                System.out.println("Run #" + i + " With " + nodes + " Nodes");
                Graph g = generateGraph(nodes);
                System.out.println(Arrays.deepToString(g.adjacency));
                Dijkstras dijkstras = new Dijkstras(g);
                FloydWarshall floyd = new FloydWarshall();
                timesDijkstra += timer(dijkstras::findAllCheapestPaths);
                timesFloyd += timer(() -> floyd.getDistance(g));
            }
            timesDijkstra /= runs;
            timesFloyd /= runs;
            times.add(new long[]{timesDijkstra, timesFloyd});
            System.out.println("Dijkstra average time for " + nodes + " nodes: " + timesDijkstra + "ms");
            System.out.println("Floyd average time for " + nodes + " nodes: " + timesFloyd + "ms");
        }
        System.out.println(Arrays.deepToString(times.toArray()));
    }

    private static void fail() {
        System.out.println("Sanity Check Failed.");
        System.exit(1);
    }
}
