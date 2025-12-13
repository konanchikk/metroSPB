package org.example.metro.model;

/**
 * Объект станции метро с индексом в графе.
 *
 * @param index индекс станции в графе
 * @param line номер линии
 * @param name название станции
 */
public record StationNode(
        int index,
        int line,
        String name
) {}
