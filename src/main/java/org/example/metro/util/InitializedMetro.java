package org.example.metro.util;

import org.example.metro.graph.MetroGraph;
import org.example.metro.model.StationNode;
import java.util.Map;

/**
 * Объект, содержащий инициализированный граф метро
 * и карту станций по их индексам.
 *
 * @param graph граф метро
 * @param stations карта станций по индексу
 */
public record InitializedMetro(
        MetroGraph graph,
        Map<Integer, StationNode> stations
) {}
