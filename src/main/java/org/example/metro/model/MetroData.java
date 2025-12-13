package org.example.metro.model;

import java.util.List;
import java.util.Map;

/**
 * Полная структура данных метро для загрузки из JSON.
 *
 * <p>Содержит информацию о линиях, станциях, рёбрах и пересадках.
 */
public class MetroData {

    /** Список линий метро */
    public List<Line> lines;

    /** Станции по линиям (ключ — номер линии, значение — список станций) */
    public Map<String, List<String>> stations;

    /** Рёбра графа метро */
    public List<Edge> edges;

    /** Пересадки между станциями разных линий */
    public List<List<EdgePoint>> connections;
}
