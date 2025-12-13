package org.example.metro.model;

/**
 * Модель станции метрополитена.
 *
 * <p>Содержит название станции и индекс в графе метро.
 */
public class Station {

    /** Название станции */
    private final String name;

    /** Индекс станции в графе */
    private final int index;

    /**
     * Создает станцию метро.
     *
     * @param name название станции
     * @param index индекс станции в графе
     */
    public Station(String name, int index) {
        this.name = name;
        this.index = index;
    }

    /**
     * Возвращает название станции.
     *
     * @return название станции
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает индекс станции.
     *
     * @return индекс станции
     */
    public int getIndex() {
        return index;
    }
}
