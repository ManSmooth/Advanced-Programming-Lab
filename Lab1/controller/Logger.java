package controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    public static void log(String s, boolean handler) {
        System.out.printf("[%s] %s: %s\n",
                DateTimeFormatter.ofPattern("hh:mm:ss").format(LocalDateTime.now()),
                Thread.currentThread().getStackTrace()[handler? 3: 2].getClassName(), s);
    }
}
