package org.example.metro.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.metro.model.StationNode;

import java.util.List;
import java.util.Scanner;

/**
 * Утилита для выбора станции из списка кандидатов.
 *
 * <p>Используется, если станции имеют одинаковое название
 * на разных линиях. Позволяет пользователю выбрать
 * нужную станцию через консоль.
 */
public class StationSelector {

    /** Логгер */
    private static final Logger logger = LogManager.getLogger(StationSelector.class);

    /**
     * Выбирает станцию из списка кандидатов.
     *
     * @param candidates список станций
     * @param prompt сообщение для пользователя при множественном выборе
     * @param scanner объект Scanner для ввода
     * @return выбранная станция
     */
    public static StationNode selectStation(List<StationNode> candidates, String prompt, Scanner scanner) {
        if (candidates.size() == 1) {
            return candidates.get(0);
        }

        System.out.println(prompt);
        for (int i = 0; i < candidates.size(); i++) {
            StationNode s = candidates.get(i);
            System.out.printf("%d. Линия %d - %s%n", i + 1, s.line(), s.name());
        }

        System.out.print("Выберите номер: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        StationNode selected = candidates.get(choice - 1);
        logger.debug("Пользователь выбрал станцию: {} (линия {})", selected.name(), selected.line());
        return selected;
    }
}
