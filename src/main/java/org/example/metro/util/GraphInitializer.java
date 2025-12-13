package org.example.metro.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.metro.graph.MetroGraph;
import org.example.metro.model.*;

import java.io.InputStream;

/**
 * Инициализатор графа метро.
 *
 * <p>Создает граф метро на основе JSON-файла. Выполняет:
 * <ul>
 *   <li>Регистрацию станций</li>
 *   <li>Добавление рёбер между станциями</li>
 *   <li>Добавление пересадок с фиксированным временем</li>
 * </ul>
 */
public class GraphInitializer {

    /**
     * Инициализирует метро из файла metro.json.
     *
     * @return объект InitializedMetro с графом и станциями
     * @throws Exception в случае ошибки чтения или парсинга JSON
     */
    public static InitializedMetro initialize() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        InputStream is = GraphInitializer.class
                .getResourceAsStream("/metro.json");

        MetroData data = mapper.readValue(is, MetroData.class);

        StationRegistry registry = new StationRegistry();

        // 1. Регистрируем станции
        data.stations.forEach((line, stationList) ->
                stationList.forEach(name ->
                        registry.register(
                                Integer.parseInt(line), name
                        )
                )
        );

        MetroGraph graph = new MetroGraph(registry.size());

        // 2. Рёбра (движение поездов)
        data.edges.forEach(edge -> {
            int from = registry
                    .get(edge.from.line, edge.from.station)
                    .index();
            int to = registry
                    .get(edge.to.line, edge.to.station)
                    .index();

            graph.addEdge(from, to, edge.time);
        });

        // 3. Пересадки (фиксированное время, например 300 сек)
        int transferTime = 300;

        data.connections.forEach(group -> {
            for (int i = 0; i < group.size(); i++) {
                for (int j = i + 1; j < group.size(); j++) {
                    StationNode a = registry.get(
                            group.get(i).line,
                            group.get(i).station
                    );
                    StationNode b = registry.get(
                            group.get(j).line,
                            group.get(j).station
                    );
                    graph.addEdge(a.index(), b.index(), transferTime);
                    graph.addEdge(b.index(), a.index(), transferTime);
                }
            }
        });

        return new InitializedMetro(
                graph,
                registry.byIndex()
        );
    }
}
