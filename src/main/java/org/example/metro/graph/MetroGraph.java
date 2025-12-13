package org.example.metro.graph;

/**
 * Граф метрополитена в виде матрицы смежности.
 *
 * <p>Хранит информацию о времени движения между станциями.
 */
public class MetroGraph {

    /** Матрица смежности */
    private final int[][] adjacencyMatrix;

    /**
     * Создает пустой граф указанного размера.
     *
     * @param size количество станций
     */
    public MetroGraph(int size) {
        adjacencyMatrix = new int[size][size];
    }

    /**
     * Добавляет ребро между станциями.
     *
     * @param from индекс начальной станции
     * @param to индекс конечной станции
     * @param time время движения в секундах
     */
    public void addEdge(int from, int to, int time) {
        adjacencyMatrix[from][to] = time;
        adjacencyMatrix[to][from] = time;
    }

    /**
     * Возвращает матрицу смежности графа метро.
     *
     * @return матрица смежности (время в секундах между станциями)
     */
    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }
}
