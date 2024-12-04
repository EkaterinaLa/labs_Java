package org.misis.tp.ttf.lab3;

import java.util.Objects;

public class Logger {
    // единственный экземпляр класса
    private static Logger instance;

    // конструктор приватный - самостоятельно создать экземпляр класса нельзя
    private Logger() {}

    // метод для получения одиночки
    public static Logger getInstance() {
        if (Objects.isNull(instance)) {
            instance = new Logger();
        }

        return instance;
    }

    // поведение одиночки
    public void log(String message) {
        System.out.println("[GAME LOG]: " + message);
    }

}

