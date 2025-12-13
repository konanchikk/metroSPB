package org.example.metro.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.metro.model.StationNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Реестр станций метро.
 *
 * <p>Хранит станции по уникальному ключу "линия:название".
 * Обеспечивает регистрацию, поиск по индексу, линии и имени.
 */
public class StationRegistry {

    /** Логгер */
    private static final Logger logger = LogManager.getLogger(StationRegistry.class);

    /** Станции по ключу "line:name" */
    private final Map<String, StationNode> stations = new HashMap<>();

    /** Счётчик уникальных индексов */
    private int indexCounter = 0;

    /**
     * Регистрирует станцию, если ещё не зарегистрирована.
     *
     * @param line номер линии
     * @param name название станции
     * @return объект StationNode
     */
    public StationNode register(int line, String name) {
        String key = line + ":" + name;
        StationNode node = stations.computeIfAbsent(
                key,
                k -> new StationNode(indexCounter++, line, name)
        );
        logger.debug("Зарегистрирована станция: {} (индекс {})", name, node.index());
        return node;
    }

    /**
     * Возвращает количество зарегистрированных станций.
     */
    public int size() {
        return stations.size();
    }

    /**
     * Получает станцию по линии и имени.
     *
     * @param line номер линии
     * @param name название станции
     * @return объект StationNode или null
     */
    public StationNode get(int line, String name) {
        return stations.get(line + ":" + name);
    }

    /**
     * Возвращает отображение индекса на станцию.
     */
    public Map<Integer, StationNode> byIndex() {
        return stations.values().stream()
                .collect(Collectors.toMap(
                        StationNode::index,
                        s -> s
                ));
    }

    /**
     * Находит все станции с указанным названием.
     *
     * @param name название станции
     */
    public List<StationNode> findByName(String name) {
        return stations.values().stream()
                .filter(station -> station.name().equals(name))
                .collect(Collectors.toList());
    }

    /**
     * Находит станцию по линии и названию.
     *
     * @param line номер линии
     * @param name название станции
     */
    public StationNode findByLineAndName(int line, String name) {
        return stations.get(line + ":" + name);
    }
}
