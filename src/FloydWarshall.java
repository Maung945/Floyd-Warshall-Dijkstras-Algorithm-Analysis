/**
 * Author: Myo Aung & Jihun Choi:
 * Class: CS3310-02 - Project2 Fall-2022
 * FloydWarshall.java
 */
import java.util.LinkedList;
import java.util.Vector;

public class FloydWarshall {
    public final int MAX_WEIGHT = 100;

    int[][] getDistance(Graph graph) {
        int matrix[][] = new int[graph.adjacency.length][graph.adjacency.length];
        int path[][] = new int[graph.adjacency.length][graph.adjacency.length];
        int i, j, k;
        for (i = 0; i < graph.adjacency.length; i++) {
            for (j = 0; j < graph.adjacency.length; j++) {
                matrix[i][j] = graph.adjacency[i][j];
                path[i][j] = j;
            }
        }
        for (k = 0; k < graph.adjacency.length; k++) {                          // Floyd-  Algorithm
            for (i = 0; i < graph.adjacency.length; i++) {
                for (j = 0; j < graph.adjacency.length; j++) {
                    if (matrix[i][k] + matrix[k][j] < matrix[i][j]) {
                        matrix[i][j] = matrix[i][k] + matrix[k][j];
                        path[i][j] = path[i][k];
                    }
                }
            }
        }
        printMatrix(matrix, path);
        return matrix;
    }

    static Vector<Integer> constructPath(int u, int v, int path[][]) {
        if (path[u][v] == 101)
            return null;
        Vector<Integer> shortestpath = new Vector<Integer>();
        shortestpath.add(u);
        while (u != v) {
            u = path[u][v];
            shortestpath.add(u);
        }
        int n = shortestpath.size();
        for (int i = 0; i < n - 1; i++)
            System.out.print(shortestpath.get(i) + " -> ");
        System.out.print(shortestpath.get(n - 1) + "\n");
        return shortestpath;
    }

    private void findPath(int[][] matrix, int[][] path, int start) {
        int start_save = start;
        System.out.println("start: " + start);
        for (int i = 0; i < path.length; i++) {
            start = start_save;
            //System.out.println(path[5][0]);
            if (matrix[start][i] == 101) {
                System.out.println("No path from " + start_save + " to " + i);
                continue;
            }
            if (start == i) {
                System.out.println(start);
                continue;
            }
            LinkedList<Integer> shortestPath = new LinkedList<Integer>();
            while (start != i) {
                start = path[start][i];
                shortestPath.add(start);
            }
            System.out.print(start_save + " -> " + shortestPath.remove());
            while (!shortestPath.isEmpty())
                System.out.print(" -> " + shortestPath.remove());
            System.out.println();
        }
    }

    private void printMatrix(int matrix[][], int path[][]) {
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix.length; ++j) {
                if (matrix[i][j] == MAX_WEIGHT + 1)
                    System.out.print("INF ");
                else
                    System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        for (int i = 0; i < matrix.length; i++) {
            findPath(matrix, path, i);
            System.out.println();
        }
    }
}
