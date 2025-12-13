package org.example.metro.graph;

import java.util.*;

/**
 * Реализация алгоритма Дейкстры.
 *
 * <p>Используется для поиска кратчайшего пути
 * во взвешенном графе с неотрицательными весами.
 */
public class DijkstraAlgorithm {

    /**
     * Выполняет поиск кратчайшего пути между двумя вершинами.
     *
     * @param graph матрица смежности графа
     * @param start индекс начальной вершины
     * @param end индекс конечной вершины
     * @return список индексов вершин пути или пустой список
     */
    public List<Integer> findShortestPath(int[][] graph, int start, int end) {

        int size = graph.length;
        int[] distances = new int[size];
        int[] previous = new int[size];
        boolean[] visited = new boolean[size];

        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(previous, -1);
        distances[start] = 0;

        for (int i = 0; i < size; i++) {
            int current = getMinDistanceVertex(distances, visited);
            visited[current] = true;

            for (int neighbor = 0; neighbor < size; neighbor++) {
                if (graph[current][neighbor] > 0 && !visited[neighbor]) {
                    int newDist = distances[current] + graph[current][neighbor];
                    if (newDist < distances[neighbor]) {
                        distances[neighbor] = newDist;
                        previous[neighbor] = current;
                    }
                }
            }
        }

        return buildPath(previous, start, end);
    }

    /**
     * Находит непосещённую вершину с минимальным расстоянием.
     */
    private int getMinDistanceVertex(int[] distances, boolean[] visited) {
        return Arrays.stream(
                        java.util.stream.IntStream.range(0, distances.length)
                                .filter(i -> !visited[i])
                                .toArray()
                ).boxed()
                .min(Comparator.comparingInt(i -> distances[i]))
                .orElseThrow();
    }

    /**
     * Восстанавливает путь по массиву предыдущих вершин.
     */
    private List<Integer> buildPath(int[] previous, int start, int end) {
        LinkedList<Integer> path = new LinkedList<>();
        for (int at = end; at != -1; at = previous[at]) {
            path.addFirst(at);
        }
        return path.getFirst() == start ? path : List.of();
    }
}
