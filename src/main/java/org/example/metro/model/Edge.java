package org.example.metro.model;

/**
 * Модель ребра графа метро.
 *
 * <p>Содержит начальную и конечную точки и время движения между ними в секундах.
 */
public class Edge {

    /** Начальная точка ребра */
    public EdgePoint from;

    /** Конечная точка ребра */
    public EdgePoint to;

    /** Время движения между станциями (в секундах) */
    public int time;
}
