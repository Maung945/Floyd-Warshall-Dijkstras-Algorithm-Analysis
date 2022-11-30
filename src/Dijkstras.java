/**
 * Author: Myo Aung & Jihun Choi:
 * Class: CS3310-02 - Project2 Fall-2022
 * Dijkstras.java
 */
import java.util.Arrays;
import java.util.HashSet;

public class Dijkstras {
    public final int MAX_WEIGHT = 100;
    Graph graph;
    public final int[][] costPrevTable;
    private final HashSet<Integer> visited = new HashSet<>();

    public Dijkstras(Graph graph) {
        this.graph = graph;
        costPrevTable = new int[2][graph.adjacency.length];
    }

    private int getCostOf(int i) {
        return costPrevTable[0][i];
    }

    private void setCostOf(int i, int val) {
        costPrevTable[0][i] = val;
    }

    private void setPrevOf(int i, int val) {
        costPrevTable[1][i] = val;
    }

    private boolean visited(int i) {                                                  // Hash Set Contains i
        return visited.contains(i);
    }

    private int getDistance(int from, int to) {
        return graph.adjacency[from][to];
    }

    private void initialize(int start) {
        visited.clear();
        visited.add(start);
        for (int i = 0; i < graph.adjacency.length; i++) {
            int cost = getDistance(start, i);
            setCostOf(i, cost);
            setPrevOf(i, start);
        }
    }

    private void update(int i) {
        int selfCost = getCostOf(i);
        for (int j = 0; j < graph.adjacency.length; j++) {
            int cost = getDistance(i, j);
            int currentCost = getCostOf(j);
            if (visited(j))
                continue;
            if (cost == MAX_WEIGHT + 1)
                continue;
            if (cost + selfCost < currentCost || currentCost == MAX_WEIGHT + 1) {
                setCostOf(j, cost + selfCost);
                setPrevOf(j, i);
            }
        }
    }

    private int lowestNonVisited() {                                                // This function returns lowest non-visited
        int lowestIndex = MAX_WEIGHT + 1;                                           // Set it to MAX
        int cheapestCost = MAX_WEIGHT + 1;                                          // Set it to MAX
        for (int i = 0; i < graph.adjacency.length; i++) {
            if (visited(i))
                continue;
            int cost = getCostOf(i);
            if (cost == MAX_WEIGHT + 1)
                continue;
            if (cost < cheapestCost || lowestIndex == MAX_WEIGHT + 1) {             // When found lowest
                lowestIndex = i;
                cheapestCost = cost;
            }
        }
        return lowestIndex;
    }

    public void findCheapestPath(int start) {
        initialize(start);
        while (true) {
            int cheapest = lowestNonVisited();
            if (cheapest == MAX_WEIGHT + 1)
                break;
            visited.add(cheapest);
            update(cheapest);
        }
    }

    public void findAllCheapestPaths() {                                            //  Apply Dijkstra's Algo to all the indexes
        for (int i = 0; i < graph.adjacency.length; i++) {
            System.out.println("Start: " + i);
            findCheapestPath(i);
            printTable();
            System.out.println();
            System.out.println("---------------------------------------");
        }
    }

    public void printTable() {
        String[] costPrevTable_INF = new String[costPrevTable[0].length];
        for (int i = 0; i < costPrevTable[0].length; i++) {
            if (costPrevTable[0][i] == MAX_WEIGHT + 1) {
                costPrevTable_INF[i] = "INF";
                continue;
            }
            costPrevTable_INF[i] = String.valueOf(costPrevTable[0][i]);
        }
        System.out.println("Cost: " + Arrays.toString(costPrevTable_INF));
        System.out.println("Prev: " + Arrays.toString(costPrevTable[1]));
        System.out.println("Visited: " + Arrays.toString(visited.toArray()));
    }
}

