package org.example.metro;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.metro.graph.DijkstraAlgorithm;
import org.example.metro.model.StationNode;
import org.example.metro.util.GraphInitializer;
import org.example.metro.util.InitializedMetro;
import org.example.metro.util.StationSelector;

import java.util.List;
import java.util.Scanner;

/**
 * Главный класс консольного приложения для поиска кратчайшего маршрута
 * между станциями метрополитена Санкт-Петербурга.
 *
 * <p>Приложение выполняет следующие действия:
 * <ul>
 *     <li>загружает данные метро из JSON-файла;</li>
 *     <li>строит граф станций с весами рёбер (время в секундах);</li>
 *     <li>принимает от пользователя начальную и конечную станции;</li>
 *     <li>использует алгоритм Дейкстры для поиска оптимального маршрута;</li>
 *     <li>выводит маршрут и общее время поездки.</li>
 * </ul>
 *
 * <p>Проект предназначен для учебных целей.
 */
public class Main {

    /** Логгер приложения */
    private static final Logger logger =
            LogManager.getLogger(Main.class);

    /**
     * Точка входа в программу.
     *
     * <p>Последовательность работы метода:
     * <ol>
     *     <li>инициализация данных метро;</li>
     *     <li>ввод начальной станции;</li>
     *     <li>ввод конечной станции;</li>
     *     <li>поиск кратчайшего маршрута;</li>
     *     <li>вывод результата.</li>
     * </ol>
     *
     * @param args аргументы командной строки (не используются)
     * @throws Exception при ошибке загрузки данных метро
     */
    public static void main(String[] args) throws Exception {

        logger.info("Запуск приложения метро");

        InitializedMetro metro = GraphInitializer.initialize();
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm();

        Scanner scanner = new Scanner(System.in);

        // Ввод начальной станции
        System.out.print("Введите начальную станцию: ");
        String startName = scanner.nextLine();

        List<StationNode> startCandidates = metro.stations().values().stream()
                .filter(s -> s.name().equalsIgnoreCase(startName))
                .toList();

        if (startCandidates.isEmpty()) {
            logger.error("Начальная станция не найдена");
            System.out.println("Станция не найдена!");
            return;
        }

        StationNode startStation = StationSelector.selectStation(
                startCandidates,
                "Найдено несколько станций:",
                scanner
        );

        // Ввод конечной станции
        System.out.print("Введите конечную станцию: ");
        String endName = scanner.nextLine();

        List<StationNode> endCandidates = metro.stations().values().stream()
                .filter(s -> s.name().equalsIgnoreCase(endName))
                .toList();

        if (endCandidates.isEmpty()) {
            logger.error("Конечная станция не найдена");
            System.out.println("Станция не найдена!");
            return;
        }

        StationNode endStation = StationSelector.selectStation(
                endCandidates,
                "Найдено несколько станций:",
                scanner
        );

        // Поиск маршрута
        List<Integer> path = dijkstra.findShortestPath(
                metro.graph().getAdjacencyMatrix(),
                startStation.index(),
                endStation.index()
        );

        if (path.isEmpty()) {
            logger.warn("Маршрут не найден");
            System.out.println("Путь не найден!");
            return;
        }

        // Подсчёт времени
        int totalTime = 0;
        int[][] matrix = metro.graph().getAdjacencyMatrix();

        for (int i = 0; i < path.size() - 1; i++) {
            totalTime += matrix[path.get(i)][path.get(i + 1)];
        }

        logger.info("Маршрут успешно найден");

        // Вывод результата
        System.out.println("\n=== МАРШРУТ ===");
        System.out.printf("От: %s (линия %d)%n",
                startStation.name(), startStation.line());
        System.out.printf("До: %s (линия %d)%n",
                endStation.name(), endStation.line());
        System.out.println("----------------");
        System.out.printf("Общее время: %d мин.%n", totalTime / 60);

        System.out.println("\nПо станциям:");
        for (Integer index : path) {
            StationNode station = metro.stations().get(index);
            System.out.printf("Линия %d: %s%n",
                    station.line(), station.name());
        }

        scanner.close();
    }
}
